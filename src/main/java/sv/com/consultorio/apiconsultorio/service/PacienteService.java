package sv.com.consultorio.apiconsultorio.service;

import org.springframework.data.domain.Page;

import sv.com.consultorio.apiconsultorio.controller.request.PacienteRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.PacienteResponse;

public interface PacienteService {
	
	Page<PacienteResponse> getAllFilteredByDui(final Integer page, final Integer size, String dui);
	
	PacienteResponse findById(final Long id);

	BaseResponse save(final PacienteRequest pacienteRequest);

	BaseResponse update(final Long pacienteId,final PacienteRequest pacienteRequest);

}
