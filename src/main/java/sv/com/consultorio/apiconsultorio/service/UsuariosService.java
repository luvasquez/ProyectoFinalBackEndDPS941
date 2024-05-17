package sv.com.consultorio.apiconsultorio.service;

import java.util.List;
import sv.com.consultorio.apiconsultorio.controller.request.CambioPasswordRequest;
import sv.com.consultorio.apiconsultorio.controller.request.UsuarioRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.UsuariosResponse;

public interface UsuariosService {
	
	List<UsuariosResponse> findAllByCorreo(String correo);
	
	BaseResponse changeEstadoActivo(final Long id);
	
	BaseResponse registrarUsuario(final UsuarioRequest usuarioRequest);
	
	BaseResponse resetPassword(final String correo);
	
	BaseResponse changePassword(final CambioPasswordRequest cambioPasswordRequest);

}