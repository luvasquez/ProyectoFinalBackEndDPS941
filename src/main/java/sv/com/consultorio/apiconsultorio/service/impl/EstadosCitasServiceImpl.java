package sv.com.consultorio.apiconsultorio.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.EstadosCitasResponse;
import sv.com.consultorio.apiconsultorio.model.EstadosCitas;
import sv.com.consultorio.apiconsultorio.repository.EstadosCitasRepository;
import sv.com.consultorio.apiconsultorio.service.EstadosCitasService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadosCitasServiceImpl implements EstadosCitasService {

    @NonNull
    private final EstadosCitasRepository estadosCitasRepository;

    @Override
    public List<EstadosCitasResponse> findAll() {
        List<EstadosCitasResponse> ret = new ArrayList<>();

        for(EstadosCitas ec : estadosCitasRepository.findAll()) {
            ret.add(
                    EstadosCitasResponse.builder()
                            .codigo(ec.getCodigo())
                            .descripcion(ec.getDescripcion())
                            .build()
            );
        }

        return ret;
    }

    @Override
    public EstadosCitasResponse findById(String id) {
        EstadosCitas ec = estadosCitasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return EstadosCitasResponse.builder()
                .codigo(ec.getCodigo())
                .descripcion(ec.getDescripcion())
                .build();
    }

    @Override
    public BaseResponse save(GeneralRequest estadoCitaRequest) {

        Optional<EstadosCitas> aux = estadosCitasRepository.findById(estadoCitaRequest.getCodigo());

        if(aux.isPresent()) {
            return BaseResponse.builder()
                    .code("-1")
                    .messages("El codigo ingresado ya existe")
                    .build();
        } else {
            EstadosCitas saveObj = new EstadosCitas();
            saveObj.setCodigo(estadoCitaRequest.getCodigo());
            saveObj.setDescripcion(estadoCitaRequest.getDescripcion());

            estadosCitasRepository.save(saveObj);
        }


        return BaseResponse.builder()
                .code("1")
                .messages("Registro guardado exitosamente")
                .build();
    }

    @Override
    public BaseResponse remove(String id) {
        Optional<EstadosCitas> aux = estadosCitasRepository.findById(id);

        if(aux.isPresent()) {

            estadosCitasRepository.delete(aux.get());

            return BaseResponse.builder()
                    .code("1")
                    .messages("Registro eliminado con exito")
                    .build();
        }

        return BaseResponse.builder()
                .code("-1")
                .messages("No se encontro ningun registro con ese codigo")
                .build();
    }

    @Override
    public BaseResponse update(GeneralRequest estadoCitaRequest) {
        Optional<EstadosCitas> aux = estadosCitasRepository.findById(estadoCitaRequest.getCodigo());

        if(aux.isPresent()) {
            EstadosCitas up = aux.get();

            up.setDescripcion(estadoCitaRequest.getDescripcion());

            estadosCitasRepository.save(up);

            return BaseResponse.builder()
                    .code("1")
                    .messages("Registro actualizado con exito")
                    .build();
        }

        return BaseResponse.builder()
                .code("-1")
                .messages("No se encontro ningun registro con ese codigo")
                .build();
    }


}
