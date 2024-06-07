package sv.com.consultorio.apiconsultorio.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import sv.com.consultorio.apiconsultorio.controller.request.DoctoresRequest;
import sv.com.consultorio.apiconsultorio.controller.request.FinalizarCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.RegistroCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.request.ReprogramacionCitaRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitaFullDataResponse;
import sv.com.consultorio.apiconsultorio.controller.response.CitasResponse;
import sv.com.consultorio.apiconsultorio.controller.response.DoctoresResponse;
import sv.com.consultorio.apiconsultorio.controller.response.RegistroCitaResponse;
import sv.com.consultorio.apiconsultorio.controller.response.SelectOptionResponse;
import sv.com.consultorio.apiconsultorio.service.CitasService;
import sv.com.consultorio.apiconsultorio.service.DoctoresService;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor()
public class CitasController {
	
	@NonNull
	private CitasService citasService;
	
	@GetMapping("/citasPendientes")
	@ResponseStatus(HttpStatus.OK)
	public Page<CitasResponse> filtered(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "fecha",required = false, defaultValue = "") final String fecha){
		
		LocalDate fechaCita = null;
		
		if (fecha != null && !"".equals(fecha)) {
			fechaCita = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		
		return citasService.getProgramadaReprogramadasFilteredByFecha(page, size, fechaCita);
	}
	
	@GetMapping("/historial")
	@ResponseStatus(HttpStatus.OK)
	public Page<CitasResponse> historial(@RequestParam(value = "page") final Integer page,
			@RequestParam(value = "size") final Integer size,
			@RequestParam(value = "fecha",required = false, defaultValue = "") final String fecha){
		
		LocalDate fechaCita = null;
		
		if (fecha != null && !"".equals(fecha)) {
			fechaCita = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		
		return citasService.getHistorialFilteredByFecha(page, size, fechaCita);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CitasResponse getOne(@PathVariable Long id){
		return citasService.findById(id);
	}
	
	@GetMapping("/registroCita/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RegistroCitaResponse getRegistroCita(@PathVariable Long id){
		return citasService.findRegistroCitaById(id);
	}
	
	@GetMapping("/fullData/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CitaFullDataResponse fullData(@PathVariable Long id){
		return citasService.findByIdFullData(id);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
    public BaseResponse saveEntity(@RequestBody RegistroCitaRequest registroCitaRequest) {
        return citasService.registrarCita(registroCitaRequest);
    }
	
	@PutMapping("/editProgramadaOrRepro/{citaId}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse update(@PathVariable Long citaId,@RequestBody RegistroCitaRequest registroCitaRequest){
		return citasService.editCitaProgramadaOrReprogramada(citaId, registroCitaRequest);
	}
	
	@PutMapping("/reprogramar/{citaId}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse reprogramar(@PathVariable Long citaId,@RequestBody ReprogramacionCitaRequest reprogramacionCitaRequest){
		return citasService.reprogramarCita(citaId, reprogramacionCitaRequest);
	}
	
	@PutMapping("/finalizar/{citaId}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse finalizar(@PathVariable Long citaId,@RequestBody FinalizarCitaRequest finalizarCitaRequest){
		return citasService.finalizar(citaId, finalizarCitaRequest);
	}
	
	@PatchMapping("/cancelar/{citaId}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse cancelar(@PathVariable Long citaId){
		return citasService.cancelar(citaId);
	}
	
	@GetMapping("/selectPacientes")
	@ResponseStatus(HttpStatus.OK)
	public List<SelectOptionResponse> selectPacientes(){
		return citasService.getAllPacientes();
	}
	
	@GetMapping("/selectDoctores")
	@ResponseStatus(HttpStatus.OK)
	public List<SelectOptionResponse> selectDoctores(){
		return citasService.getAllDoctores();
	}
	
	@GetMapping("/selectConsultorios")
	@ResponseStatus(HttpStatus.OK)
	public List<SelectOptionResponse> selectConsultorios(){
		return citasService.getAllConsultorios();
	}
	
}