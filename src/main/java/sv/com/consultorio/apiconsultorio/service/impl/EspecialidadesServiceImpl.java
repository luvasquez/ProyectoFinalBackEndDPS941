package sv.com.consultorio.apiconsultorio.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.GeneralResponse;
import sv.com.consultorio.apiconsultorio.model.Especialidades;
import sv.com.consultorio.apiconsultorio.repository.EspecialidadesRepository;
import sv.com.consultorio.apiconsultorio.service.EspecialidadesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EspecialidadesServiceImpl implements EspecialidadesService {

    @NonNull
    private final EspecialidadesRepository especialidadesRepository;

    @Override
    public List<GeneralResponse> findAll() {
        List<GeneralResponse> ret = new ArrayList<>();

        for(Especialidades ec : especialidadesRepository.findAll()) {
            ret.add(
                    GeneralResponse.builder()
                            .id(ec.getId())
                            .codigo(ec.getCodigo())
                            .descripcion(ec.getDescripcion())
                            .fechaDeRegistro(ec.getFechaDeRegistro())
                            .build()
            );
        }

        return ret;
    }

    @Override
    public GeneralResponse findById(Long id) {
        Especialidades ec = especialidadesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return GeneralResponse.builder()
                .id(ec.getId())
                .codigo(ec.getCodigo())
                .descripcion(ec.getDescripcion())
                .fechaDeRegistro(ec.getFechaDeRegistro())
                .build();
    }

    @Override
    public BaseResponse save(GeneralRequest especialidadRequest) {
        Especialidades saveObj = new Especialidades();
        saveObj.setCodigo(especialidadRequest.getCodigo());
        saveObj.setDescripcion(especialidadRequest.getDescripcion());
        saveObj.setFechaDeRegistro(new Date());

        especialidadesRepository.save(saveObj);

        return BaseResponse.builder()
                .code("1")
                .messages("Registro guardado exitosamente")
                .build();
    }

    @Override
    public BaseResponse remove(Long id) {
        Optional<Especialidades> aux = especialidadesRepository.findById(id);

        if(aux.isPresent()) {

            especialidadesRepository.delete(aux.get());

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
    public BaseResponse update(GeneralRequest especialidadRequest) {
        Optional<Especialidades> aux = especialidadesRepository.findById(especialidadRequest.getId());

        if(aux.isPresent()) {
            Especialidades up = aux.get();

            up.setCodigo(especialidadRequest.getCodigo());
            up.setDescripcion(especialidadRequest.getDescripcion());

            especialidadesRepository.save(up);

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
