package sv.com.consultorio.apiconsultorio.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.com.consultorio.apiconsultorio.controller.request.GeneralRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.GeneralResponse;
import sv.com.consultorio.apiconsultorio.service.EspecialidadesService;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
@RequiredArgsConstructor()
public class EspecialidadesController {

    @NonNull
    private final EspecialidadesService especialidadesService;

    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    public List<GeneralResponse> findAll () {
        return especialidadesService.findAll();
    }

    @GetMapping("/findbycode/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse findByCode (@PathVariable Long id) {
        return especialidadesService.findById(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse save (@Valid @RequestBody GeneralRequest request) {
        return especialidadesService.save(request);
    }

    @DeleteMapping("/delete/{code}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse delete (@PathVariable Long code) {
        return especialidadesService.remove(code);
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse update (@Valid @RequestBody GeneralRequest request) {
        return especialidadesService.update(request);
    }


}
