package sv.com.consultorio.apiconsultorio.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.controller.request.FinalizarCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.RegistroCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.ReprogramacionCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitaFullDataResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitasResponse;
import sv.com.consultorio.apiconsultorio.controller.response.RegistroCitaResponse;
import sv.com.consultorio.apiconsultorio.controller.response.SelectOptionResponse;
import sv.com.consultorio.apiconsultorio.model.Citas;
import sv.com.consultorio.apiconsultorio.model.Consultorios;
import sv.com.consultorio.apiconsultorio.model.Doctores;
import sv.com.consultorio.apiconsultorio.model.Paciente;
import sv.com.consultorio.apiconsultorio.repository.CitasRepository;
import sv.com.consultorio.apiconsultorio.repository.ConsultoriosRepository;
import sv.com.consultorio.apiconsultorio.repository.DoctoresRepository;
import sv.com.consultorio.apiconsultorio.repository.EstadosCitasRepository;
import sv.com.consultorio.apiconsultorio.repository.PacienteRepository;
import sv.com.consultorio.apiconsultorio.service.CitasService;


@Service
@RequiredArgsConstructor
public class CitasServiceImpl implements CitasService {
	
	
	@NonNull
	private CitasRepository citasRepository;
	
	@NonNull
	private ConsultoriosRepository consultoriosRepository;
	
	@NonNull
	private PacienteRepository pacienteRepository;
	
	@NonNull
	private DoctoresRepository doctoresRepository;
	
	@NonNull
	private EstadosCitasRepository estadosCitasRepository;
	
	@Override
	public Page<CitasResponse> getProgramadaReprogramadasFilteredByFecha(Integer page, Integer size, LocalDate fecha) {
		Pageable pageable = PageRequest.of(page, size);
		
		List<String> listaEstado = Arrays.asList("PROGRA", "REPROG");
		
		Page<Citas> data = 
				(fecha != null) 
				? citasRepository.findProgramadasReprogramadasLikeByFechaCita(listaEstado, fecha, pageable)
				: citasRepository.findProgramadasReprogramadas(listaEstado,pageable);
		
		return data.map(this::convertEntityToResponse);
	}
	
	@Override
	public Page<CitasResponse> getHistorialFilteredByFecha(Integer page, Integer size, LocalDate fecha) {
		
		List<String> listaEstado = Arrays.asList("COMPL", "CANCE");
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Citas> data = 
				(fecha != null) 
				? citasRepository.findProgramadasReprogramadasLikeByFechaCita(listaEstado, fecha, pageable)
						: citasRepository.findProgramadasReprogramadas(listaEstado,pageable);
		
		return data.map(this::convertEntityToResponse);
	}
	
	@Override
	public CitasResponse findById(Long id) {
		return convertEntityToResponse(fechCitaById(id));
	}
	
	@Override
	public RegistroCitaResponse findRegistroCitaById(Long id) {
		Citas cita = fechCitaById(id);
		return RegistroCitaResponse.builder().citaId(id).pacienteId(cita.getPaciente().getId())
				.doctorId(cita.getDoctor().getId()).consultorioId(cita.getConsultorio().getId())
				.fechaCita(cita.getFechaCita()).build();
	}

	@Override
	public BaseResponse registrarCita(RegistroCitaRequest registroCitaRequest) {
		
		Citas cita = new Citas();
		cita.setConsultorio(consultoriosRepository.getReferenceById(registroCitaRequest.getConsultorioId()));
		cita.setDoctor(doctoresRepository.getReferenceById(registroCitaRequest.getDoctorId()));
		cita.setPaciente(pacienteRepository.getReferenceById(registroCitaRequest.getPacienteId()));
		cita.setFechaCita(registroCitaRequest.getFechaCita());
		cita.setEstadoCita(estadosCitasRepository.findByCodigo("PROGRA"));
		cita.setFechaRegistro(LocalDate.now());
		citasRepository.save(cita);
		return BaseResponse.builder()
                .code("1")
                .messages("Cita registrada existosamente!!!")
                .build();
	}

	@Override
	public BaseResponse editCitaProgramadaOrReprogramada(Long citaId, RegistroCitaRequest registroCitaRequest) {
		
		Citas citasDb = fechCitaById(citaId);
		citasDb.setConsultorio(consultoriosRepository.getReferenceById(registroCitaRequest.getConsultorioId()));
		citasDb.setDoctor(doctoresRepository.getReferenceById(registroCitaRequest.getDoctorId()));
		citasDb.setPaciente(pacienteRepository.getReferenceById(registroCitaRequest.getPacienteId()));
		citasDb.setFechaCita(registroCitaRequest.getFechaCita());
		citasRepository.save(citasDb);
		return BaseResponse.builder()
                .code("1")
                .messages("Cita editada existosamente!!!")
                .build();
	}

	@Override
	public BaseResponse reprogramarCita(Long citaId, ReprogramacionCitaRequest request) {
		
		Citas citasDb = fechCitaById(citaId);
		//cambiazo de fechas por las busquedas
		citasDb.setFechaReprogramacion(citasDb.getFechaCita());
		citasDb.setFechaCita(request.getFechaReprogramacion());
		citasDb.setComentariosAdicionales(request.getComentariosAdicionales());
		citasDb.setEstadoCita(estadosCitasRepository.findByCodigo("REPROG"));
		
		citasRepository.save(citasDb);
		return BaseResponse.builder()
                .code("1")
                .messages("Cita Reprogramada existosamente!!!")
                .build();
	}

	@Override
	public BaseResponse finalizar(Long citaId, FinalizarCitaRequest request) {
		
		Citas citasDb = fechCitaById(citaId);
		citasDb.setEstadoCita(estadosCitasRepository.findByCodigo("COMPL"));
		citasDb.setFechaFin(LocalDate.now());
		
		citasDb.setTratamiento(request.getTratamiento());
		citasDb.setOrigen(request.getOrigen());
		citasDb.setMedicinas(request.getMedicinas());
		
		citasRepository.save(citasDb);
		return BaseResponse.builder()
                .code("1")
                .messages("Cita Completada existosamente!!!")
                .build();
	}
	
	@Override
	public BaseResponse cancelar(Long citaId) {
		Citas citasDb = fechCitaById(citaId);
		citasDb.setEstadoCita(estadosCitasRepository.findByCodigo("CANCE"));
		citasDb.setFechaFin(LocalDate.now());
		citasRepository.save(citasDb);
		return BaseResponse.builder()
                .code("1")
                .messages("Cita Cancelada existosamente!!!")
                .build();
	}
	
	@Override
	public List<SelectOptionResponse> getAllPacientes() {
		List<Paciente> list = pacienteRepository.findAll();
		return list.stream().map(paciente -> SelectOptionResponse.builder()
				.label(paciente.getDui().concat(" - ").concat(paciente.getNombreCompleto()))
				.value(paciente.getId())
				.build()).toList();
	}

	@Override
	public List<SelectOptionResponse> getAllDoctores() {
		List<Doctores> list = doctoresRepository.findAll();
		return list.stream().map(doctor -> SelectOptionResponse.builder()
				.label(doctor.getDui().concat(" - ").concat(doctor.getNombreCompleto()))
				.value(doctor.getId())
				.build()).toList();
	}

	@Override
	public List<SelectOptionResponse> getAllConsultorios() {
		List<Consultorios> list = consultoriosRepository.findAll();
		return list.stream().map(consultorio -> SelectOptionResponse.builder()
				.label(consultorio.getDescripcion())
				.value(consultorio.getId())
				.build()).toList();
	}
	
	@Override
	public CitaFullDataResponse findByIdFullData(Long id) {
		Citas cita = fechCitaById(id);
		return CitaFullDataResponse.builder()
				.citaId(id)
				.paciente(cita.getPaciente().getNombres().concat(" ").concat(cita.getPaciente().getApellidos()))
				.doctor(cita.getDoctor().getNombres().concat(" ").concat(cita.getDoctor().getApellidos()))
				.consultorio(cita.getConsultorio().getDescripcion())
				.fechacita(cita.getFechaCita())
				.fechaFin(cita.getFechaFin())
				.fechaReprogramada(cita.getFechaReprogramacion())
				.estado(cita.getEstadoCita().getDescripcion())
				.origen(cita.getOrigen())
				.tratamiento(cita.getTratamiento())
				.medicinas(cita.getMedicinas())
				.comentariosAdiccionales(cita.getComentariosAdicionales())
				.build();
	}
	
	
	private Citas fechCitaById(final Long id) {
		return citasRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	private CitasResponse convertEntityToResponse (Citas entity) {
		return CitasResponse.builder()
				.citaId(entity.getId())
				.paciente(entity.getPaciente().getNombres().concat(" ").concat(entity.getPaciente().getApellidos()))
				.doctor(entity.getDoctor().getNombres().concat(" ").concat(entity.getDoctor().getApellidos()))
				.consultorio(entity.getConsultorio().getDescripcion())
				.fechacita(entity.getFechaCita())
				.fechaReprogramada(entity.getFechaReprogramacion())
				.build();
	}

}