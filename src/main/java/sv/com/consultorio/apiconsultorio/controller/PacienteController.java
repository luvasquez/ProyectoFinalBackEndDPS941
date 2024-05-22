package sv.com.consultorio.apiconsultorio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.auth.AuthController;
import sv.com.consultorio.apiconsultorio.controller.request.PacienteRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.PacienteResponse;
import sv.com.consultorio.apiconsultorio.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor()
public class PacienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);
	
	@NonNull
	private PacienteService pacienteService;
	
	@GetMapping({"/",""})
	@ResponseStatus(HttpStatus.OK)
	public Page<PacienteResponse> filtered(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "dui",required = false, defaultValue = "") final String dui){
		return pacienteService.getAllFilteredByDui(page, size, dui);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PacienteResponse getOne(@PathVariable Long id){
		return pacienteService.findById(id);
	}
	
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
    public BaseResponse saveEntity(@RequestBody /*@Valid*/ PacienteRequest pacienteRequest) {
        return pacienteService.save(pacienteRequest);
    }
	
	@PutMapping("/update/{pacienteId}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse update(@PathVariable Long pacienteId,@RequestBody PacienteRequest pacienteRequest){
		logger.info("Actualizando al paciente {}", pacienteId);
		return pacienteService.update(pacienteId, pacienteRequest);
	}

}