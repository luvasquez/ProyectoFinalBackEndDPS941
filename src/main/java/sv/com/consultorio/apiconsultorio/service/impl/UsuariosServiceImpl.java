package sv.com.consultorio.apiconsultorio.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.UsuariosResponse;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.UsuariosService;


@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {
	
	
	@NonNull
    private final UsuariosRepository usuariosRepository;
	
	

	@Override
	public List<UsuariosResponse> findAllByCorreo(String correo) {
		
		if (correo != null ) correo = correo.toUpperCase();
		
		List<Usuarios> dataAux = usuariosRepository.findLikeCorreo(correo);
		//List<UsuariosResponse> listReturn = dataAux.stream().map(a -> new UsuariosResponse(a.getId(), a.getRol().getDescripcion(), a.getCorreo(), a.isActivo(), a.isConfigCompletada() ))
		return dataAux.stream().map(a -> UsuariosResponse.builder().id(a.getId())
					.rol(a.getRol().getCodigo())
					.correo(a.getCorreo())
					.activo(a.isActivo())
					.configCompletada(a.isConfigCompletada())
					.build() )
				.toList();

	}

	@Override
	public BaseResponse changeEstadoActivo(Long id) {
		
		Optional<Usuarios> optionalUsuario = usuariosRepository.findById(id);
		if (optionalUsuario.isPresent()) {
			
			Usuarios usuario = optionalUsuario.get();
			
			String resultado = usuario.isActivo() ? "Usuario Inactivado Existosamente!!!" : "Usuario Activado Existosamente!!!";
			
			usuario.setActivo(!usuario.isActivo());
			usuariosRepository.save(usuario);
			return BaseResponse.builder()
                    .code("1")
                    .messages(resultado)
                    .build();
		}
		
		return BaseResponse.builder()
                .code("-1")
                .messages("No se encontro ningun registro con ese codigo")
                .build();
	}

}