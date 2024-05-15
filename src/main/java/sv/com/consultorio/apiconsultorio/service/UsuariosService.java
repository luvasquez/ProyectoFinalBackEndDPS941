package sv.com.consultorio.apiconsultorio.service;

import java.util.List;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.UsuariosResponse;

public interface UsuariosService {
	
	List<UsuariosResponse> findAllByCorreo(String correo);
	
	BaseResponse changeEstadoActivo(Long id);

}