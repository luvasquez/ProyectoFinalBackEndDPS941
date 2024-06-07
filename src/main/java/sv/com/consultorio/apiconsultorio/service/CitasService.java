package sv.com.consultorio.apiconsultorio.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import sv.com.consultorio.apiconsultorio.controller.request.FinalizarCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.RegistroCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.ReprogramacionCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitaFullDataResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitasResponse;
import sv.com.consultorio.apiconsultorio.controller.response.RegistroCitaResponse;
import sv.com.consultorio.apiconsultorio.controller.response.SelectOptionResponse;

public interface CitasService {
	
	
	Page<CitasResponse> getProgramadaReprogramadasFilteredByFecha(final Integer page, final Integer size, LocalDate fecha);
	
	Page<CitasResponse> getHistorialFilteredByFecha(final Integer page, final Integer size, LocalDate fecha);

	BaseResponse registrarCita(final RegistroCitaRequest registroCitaRequest);
	
	BaseResponse editCitaProgramadaOrReprogramada(final Long citaId, final RegistroCitaRequest registroCitaRequest);
	
	BaseResponse reprogramarCita (final Long citaId,final ReprogramacionCitaRequest reprogramacionCitaRequest);
	
	BaseResponse finalizar(final Long citaId, final FinalizarCitaRequest finalizarCitaRequest);
	
	BaseResponse cancelar(final Long citaId);

	List<SelectOptionResponse> getAllPacientes();

	List<SelectOptionResponse> getAllDoctores();

	List<SelectOptionResponse> getAllConsultorios();

	CitasResponse findById(Long id);
	
	RegistroCitaResponse findRegistroCitaById(Long id);

	CitaFullDataResponse findByIdFullData(Long id);
	
	
}