package sv.com.consultorio.apiconsultorio.controller;

import java.util.List;
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
import sv.com.consultorio.apiconsultorio.controller.request.CambioPasswordRequest;
import sv.com.consultorio.apiconsultorio.controller.request.UsuarioRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.UsuariosResponse;
import sv.com.consultorio.apiconsultorio.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor()
public class UsuariosController {
	
	@NonNull
	private UsuariosService usuariosService;
	
	@GetMapping("/findByLikeCorreo")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuariosResponse> findAll (@RequestParam(value = "correo",required = false, defaultValue = "") String correo) {
        return usuariosService.findAllByCorreo(correo);
    }
	
	@PutMapping("/changeEstadoActivo/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse changeEstadoActivo (@PathVariable Long id) {
		return usuariosService.changeEstadoActivo(id);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse create(@RequestBody UsuarioRequest usuario) {
		return usuariosService.registrarUsuario(usuario);
	}
	
	@PostMapping("/changePassword")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse changePassword(@RequestBody CambioPasswordRequest cambioPasswordRequest) {
		return usuariosService.changePassword(cambioPasswordRequest);
	}
	
}