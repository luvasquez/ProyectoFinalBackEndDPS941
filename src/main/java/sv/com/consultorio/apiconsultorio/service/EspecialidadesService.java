package sv.com.consultorio.apiconsultorio.service;

import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.GeneralResponse;

import java.util.List;

public interface EspecialidadesService {

    List<GeneralResponse> findAll();

    GeneralResponse findById(Long id);

    BaseResponse save(GeneralRequest especialidadRequest);

    BaseResponse remove(Long id);

    BaseResponse update(GeneralRequest especialidadRequest);

}
