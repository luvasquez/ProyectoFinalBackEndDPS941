package sv.com.consultorio.apiconsultorio.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.GeneralResponse;
import sv.com.consultorio.apiconsultorio.model.Consultorios;
import sv.com.consultorio.apiconsultorio.repository.ConsultoriosRepository;
import sv.com.consultorio.apiconsultorio.service.ConsultoriosService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultoriosServiceImpl implements ConsultoriosService {

    @NonNull
    private final ConsultoriosRepository consultoriosRepository;

    @Override
    public List<GeneralResponse> findAll() {
        List<GeneralResponse> ret = new ArrayList<>();

        for(Consultorios ec : consultoriosRepository.findAll()) {
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
        Consultorios ec = consultoriosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return GeneralResponse.builder()
                .id(ec.getId())
                .codigo(ec.getCodigo())
                .descripcion(ec.getDescripcion())
                .fechaDeRegistro(ec.getFechaDeRegistro())
                .build();
    }

    @Override
    public BaseResponse save(GeneralRequest consultorioRequest) {
            Consultorios saveObj = new Consultorios();
            saveObj.setCodigo(consultorioRequest.getCodigo());
            saveObj.setDescripcion(consultorioRequest.getDescripcion());
            saveObj.setFechaDeRegistro(new Date());

            consultoriosRepository.save(saveObj);

            return BaseResponse.builder()
                .code("1")
                .messages("Registro guardado exitosamente")
                .build();
    }

    @Override
    public BaseResponse remove(Long id) {
        Optional<Consultorios> aux = consultoriosRepository.findById(id);

        if(aux.isPresent()) {

            consultoriosRepository.delete(aux.get());

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
    public BaseResponse update(GeneralRequest consultorioRequest) {
        Optional<Consultorios> aux = consultoriosRepository.findById(consultorioRequest.getId());

        if(aux.isPresent()) {
            Consultorios up = aux.get();

            up.setCodigo(consultorioRequest.getCodigo());
            up.setDescripcion(consultorioRequest.getDescripcion());

            consultoriosRepository.save(up);

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
