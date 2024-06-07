package sv.com.consultorio.apiconsultorio.service.impl;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.controller.request.DoctoresRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.DoctoresResponse;
import sv.com.consultorio.apiconsultorio.model.Doctores;
import sv.com.consultorio.apiconsultorio.model.Paciente;
import sv.com.consultorio.apiconsultorio.model.Rol;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.DoctoresRepository;
import sv.com.consultorio.apiconsultorio.repository.EspecialidadesRepository;
import sv.com.consultorio.apiconsultorio.repository.RolRepository;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.DoctoresService;
import sv.com.consultorio.apiconsultorio.service.GmailService;

@Service
@RequiredArgsConstructor
public class DoctoresServiceImpl implements DoctoresService {
	
	
	private static final char[] CARACTERES_DISPONIBLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
	
	@NonNull
	private DoctoresRepository doctoresRepository;
	
	@NonNull
	private EspecialidadesRepository especialidadesRepository;
	
	@NonNull
	private UsuariosRepository usuariosRepository;
	
	@NonNull
	private final RolRepository rolRepository;
	
	@Nonnull
	private final GmailService gmailService;

	@Override
	public Page<DoctoresResponse> getAllFilteredByDui(Integer page, Integer size, String dui) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Doctores> data = doctoresRepository.findLikeByDui(dui, pageable);
		
		return data.map(this::convertEntityToResponse);
	}

	@Override
	public DoctoresResponse findById(Long id) {
		return convertEntityToResponse(fechPacienteById(id));
	}

	@Override
	public BaseResponse save(DoctoresRequest doctoresRequest) {
		
		Optional<Doctores> optionalPaciente = doctoresRepository.findByDui(doctoresRequest.getDui());
		
		if (optionalPaciente.isPresent()) {
			return BaseResponse.builder()
	                .code("-1")
	                .messages("Ya se encuentra registrado un doctor con el dui: ".concat(doctoresRequest.getDui()))
	                .build();
		}
		
		Optional<Usuarios> optionalUsuario = usuariosRepository.findByCorreo(doctoresRequest.getCorreo());
		
		if (optionalUsuario.isPresent()) {
			return BaseResponse.builder()
	                .code("-1")
	                .messages("Ya se encuentra registrado un doctor con el correo: ".concat(doctoresRequest.getCorreo()))
	                .build();
		}
		
		Rol rolDoctor =  rolRepository.findByCodigo("DOCTOR_USER");
		String passwordTemporal = generarStringAleatorio(6);
		
		Usuarios newUsuario = new Usuarios();
		newUsuario.setCorreo(doctoresRequest.getCorreo());
		newUsuario.setRol(rolDoctor);
		newUsuario.setActivo(Boolean.TRUE);
		newUsuario.setConfigCompletada(Boolean.FALSE);
		newUsuario.setPassword(passwordTemporal);
		newUsuario = usuariosRepository.save(newUsuario);
		
		
		Doctores doctorInsert = convertRequestToEntity(doctoresRequest);
		doctorInsert.setFechaRegistro(LocalDate.now());
		doctorInsert.setActivo(Boolean.TRUE);
		doctorInsert.setUsuario(newUsuario);
		
		//gmailService.sendNewMail(doctoresRequest.getCorreo(), "Registro de Doctor Correctamente",  "Tu contraseña temporal para ingresar a la App del Acilo es: ".concat(passwordTemporal) );
		
		doctoresRepository.save(doctorInsert);
		return BaseResponse.builder()
                .code("1")
                .messages("Doctor registrado existosamente!!!")
                .build();
		
	}

	@Override
	public BaseResponse update(Long pacienteId, DoctoresRequest pacienteRequest) {
		Doctores doctorDb = fechPacienteById(pacienteId);
		Doctores doctorUpdate = convertRequestToEntity(pacienteRequest);
		doctorUpdate.setId(pacienteId);
		doctorUpdate.setFechaRegistro(doctorDb.getFechaRegistro());
		doctorUpdate.setActivo(Boolean.TRUE);
		doctorUpdate.setUsuario(doctorDb.getUsuario());
		doctoresRepository.save(doctorUpdate);
		return BaseResponse.builder()
                .code("1")
                .messages("Doctor editado existosamente!!!")
                .build();
	}
	
	
	private Doctores fechPacienteById(final Long id) {
		return doctoresRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	private DoctoresResponse convertEntityToResponse (Doctores entity) {
		return DoctoresResponse.builder()
				.doctorId(entity.getId())
				.dui(entity.getDui())
				.nombres(entity.getNombres())
				.apellidos(entity.getApellidos())
				.telefono(entity.getTelefono())
				.especialidad(entity.getEspecialidad().getDescripcion())
				.especialidadId(entity.getEspecialidad().getId())
				.activo(entity.isActivo())
				.build();
	}
	
	private Doctores convertRequestToEntity(DoctoresRequest doctoresRequest) {
		Doctores doctor = new Doctores();
		doctor.setDui(doctoresRequest.getDui());
		doctor.setNombres(doctoresRequest.getNombres());
		doctor.setApellidos(doctoresRequest.getApellidos());
		doctor.setTelefono(doctoresRequest.getTelefono());
		doctor.setEspecialidad(especialidadesRepository.getReferenceById(doctoresRequest.getEspecialidadId()));
		return doctor;
	}
	
	private String generarStringAleatorio(int longitud) {
        StringBuilder builder = new StringBuilder(longitud);
        SecureRandom random = new SecureRandom();

        random.ints(longitud, 0, (CARACTERES_DISPONIBLES.length - 1))
                .mapToObj(valor -> CARACTERES_DISPONIBLES[valor])
                .forEach(builder::append);

        return builder.toString();
    }

}