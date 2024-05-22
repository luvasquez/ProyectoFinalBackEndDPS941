package sv.com.consultorio.apiconsultorio.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.controller.request.PacienteRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.PacienteResponse;
import sv.com.consultorio.apiconsultorio.model.Paciente;
import sv.com.consultorio.apiconsultorio.repository.PacienteRepository;
import sv.com.consultorio.apiconsultorio.service.PacienteService;

@Service
@RequiredArgsConstructor()
public class PacienteServiceImpl implements PacienteService {
	
	@NonNull
	private PacienteRepository pacienteRepository;

	@Override
	public Page<PacienteResponse> getAllFilteredByDui(Integer page, Integer size, String dui) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Paciente> data = pacienteRepository.findLikeByDui(dui, pageable);
		
		return data.map(this::convertEntityToResponse);
			
	}
	
	@Override
	public PacienteResponse findById(final Long id) {
		
		Paciente paciente = pacienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return convertEntityToResponse(paciente);
	}
	
	@Override
	public BaseResponse save(PacienteRequest pacienteRequest) {
		
		Optional<Paciente> optionalPaciente = pacienteRepository.findByDui(pacienteRequest.getDui());
		
		if (optionalPaciente.isPresent()) {
			return BaseResponse.builder()
	                .code("-1")
	                .messages("Ya se encuentra registrado un paciente con el dui: ".concat(pacienteRequest.getDui()))
	                .build();
		}
		
		Paciente pacienteInsert = convertRequestToEntity(pacienteRequest);
		pacienteInsert.setFechaRegistro(LocalDate.now());
		pacienteRepository.save(pacienteInsert);
		return BaseResponse.builder()
                .code("1")
                .messages("Paciente registrado existosamente!!!")
                .build();
	}
	
	@Override
	public BaseResponse update(Long pacienteId, PacienteRequest pacienteRequest) {
		Paciente pacienteDb = fechPacienteById(pacienteId);
		Paciente pacienteUpdate = convertRequestToEntity(pacienteRequest);
		pacienteUpdate.setId(pacienteId);
		pacienteUpdate.setFechaRegistro(pacienteDb.getFechaRegistro());
		pacienteRepository.save(pacienteUpdate);
		return BaseResponse.builder()
                .code("1")
                .messages("Paciente editado existosamente!!!")
                .build();
	}
	
	private Paciente fechPacienteById(final Long id) {
		return pacienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	private PacienteResponse convertEntityToResponse (Paciente entity) {
		return PacienteResponse.builder()
				.pacienteId(entity.getId())
				.usuarioId( (entity.getUsuario() != null) ? entity.getUsuario().getId() : null)
				.dui(entity.getDui())
				.nombres(entity.getNombres())
				.apellidos(entity.getApellidos())
				.fechaNacimiento(entity.getFechaNacimiento())
				.genero(entity.getGenero())
				.tipoSangre(entity.getTipoSangre())
				.peso(entity.getPeso())
				.altura(entity.getAltura())
				.telefono(entity.getTelefono())
				.direccion(entity.getDireccion())
				.build();
	}
	
	private Paciente convertRequestToEntity(PacienteRequest pacienteRequest) {
		Paciente paciente = new Paciente();
		paciente.setDui(pacienteRequest.getDui());
		paciente.setNombres(pacienteRequest.getNombres());
		paciente.setApellidos(pacienteRequest.getApellidos());
		paciente.setFechaNacimiento(pacienteRequest.getFechaNacimiento());
		paciente.setGenero(pacienteRequest.getGenero());
		paciente.setTipoSangre(pacienteRequest.getTipoSangre());
		paciente.setAltura(pacienteRequest.getAltura());
		paciente.setPeso(pacienteRequest.getPeso());
		paciente.setTelefono(pacienteRequest.getTelefono());
		paciente.setDireccion(pacienteRequest.getDireccion());
		return paciente;
	}

}