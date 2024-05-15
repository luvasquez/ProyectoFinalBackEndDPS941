package sv.com.consultorio.apiconsultorio.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.GeneralResponse;
import sv.com.consultorio.apiconsultorio.service.ConsultoriosService;

import java.util.List;

@RestController
@RequestMapping("/consultorios")
@RequiredArgsConstructor()
public class ConsultoriosController {

    @NonNull
    private final ConsultoriosService consultoriosService;

    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    public List<GeneralResponse> findAll () {
        return consultoriosService.findAll();
    }

    @GetMapping("/findbycode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse findByCode (@PathVariable Long id) {
        return consultoriosService.findById(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse save (@Valid @RequestBody GeneralRequest request) {
        return consultoriosService.save(request);
    }

    @DeleteMapping("/delete/{code}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse delete (@PathVariable Long code) {
        return consultoriosService.remove(code);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update (@Valid @RequestBody GeneralRequest request) {
        return consultoriosService.update(request);
    }


}
