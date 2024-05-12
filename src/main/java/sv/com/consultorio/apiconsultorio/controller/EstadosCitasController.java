package sv.com.consultorio.apiconsultorio.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.EstadosCitasResponse;
import sv.com.consultorio.apiconsultorio.service.EstadosCitasService;

import java.util.List;

@RestController
@RequestMapping("/estadocitas")
@RequiredArgsConstructor()
public class EstadosCitasController {

    @NonNull
    private final EstadosCitasService estadosCitasService;

    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    public List<EstadosCitasResponse> findAll () {
        return estadosCitasService.findAll();
    }

    @GetMapping("/findbycode/{code}")
    @ResponseStatus(HttpStatus.OK)
    public EstadosCitasResponse findByCode (@PathVariable String code) {
        return estadosCitasService.findById(code);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse save (@Valid @RequestBody GeneralRequest request) {
        return estadosCitasService.save(request);
    }

    @DeleteMapping("/delete/{code}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse delete (@PathVariable String code) {
        return estadosCitasService.remove(code);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update (@Valid @RequestBody GeneralRequest request) {
        return estadosCitasService.update(request);
    }

}
