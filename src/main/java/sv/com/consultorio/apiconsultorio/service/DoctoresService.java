package sv.com.consultorio.apiconsultorio.service;

import org.springframework.data.domain.Page;

import sv.com.consultorio.apiconsultorio.controller.request.DoctoresRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.DoctoresResponse;

public interface DoctoresService {
	
	Page<DoctoresResponse> getAllFilteredByDui(final Integer page, final Integer size, String dui);
	
	DoctoresResponse findById(final Long id);

	BaseResponse save(final DoctoresRequest pacienteRequest);

	BaseResponse update(final Long pacienteId,final DoctoresRequest pacienteRequest);

}
