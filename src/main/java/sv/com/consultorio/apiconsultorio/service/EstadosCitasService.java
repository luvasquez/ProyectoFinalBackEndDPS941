package sv.com.consultorio.apiconsultorio.service;

import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.EstadosCitasResponse;

import java.util.List;

public interface EstadosCitasService {

    List<EstadosCitasResponse> findAll();

    EstadosCitasResponse findById(String id);

    BaseResponse save(GeneralRequest estadoCitaRequest);

    BaseResponse remove(String id);

    BaseResponse update(GeneralRequest estadoCitaRequest);

}
