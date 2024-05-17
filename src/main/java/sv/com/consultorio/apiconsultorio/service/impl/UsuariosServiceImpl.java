package sv.com.consultorio.apiconsultorio.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.controller.request.CambioPasswordRequest;
import sv.com.consultorio.apiconsultorio.controller.request.UsuarioRequest;
import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.controller.response.UsuariosResponse;
import sv.com.consultorio.apiconsultorio.model.Rol;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.RolRepository;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.GmailService;
import sv.com.consultorio.apiconsultorio.service.UsuariosService;


@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {
	
	private static final char[] CARACTERES_DISPONIBLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
	
	@NonNull
    private final UsuariosRepository usuariosRepository;
	
	@NonNull
	private final RolRepository rolRepository;
	
	@Nonnull
	private final GmailService gmailService;
	
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
	public BaseResponse changeEstadoActivo(final Long id) {
		
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

	@Override
	public BaseResponse registrarUsuario(final UsuarioRequest usuarioRequest) {
		
		Rol rolPaciente =  rolRepository.findByCodigo("PACIENTE_USER");
		
		String passwordTemporal = generarStringAleatorio(6);
		
		Usuarios newUsuario = new Usuarios();
		newUsuario.setCorreo(usuarioRequest.getCorreo());
		newUsuario.setRol(rolPaciente);
		newUsuario.setActivo(Boolean.TRUE);
		newUsuario.setConfigCompletada(Boolean.FALSE);
		newUsuario.setPassword(passwordTemporal);
		usuariosRepository.save(newUsuario);
		
		
		//TODO: Cuando este el enlace al paciente y viene en el request relacionarlo xd
		
		gmailService.sendNewMail(usuarioRequest.getCorreo(), "Registro de usuario correctamente", 
				"Tu contraseña temporal para ingresar a la App del Acilo es: ".concat(passwordTemporal) );
		
		return BaseResponse.builder()
                .code("1")
                .messages("Usuario registrado, se enviara a tu correo una contraseña temporal para realizar el ingreso a la APP")
                .build();
	}

	@Override
	public BaseResponse resetPassword(final String correo) {
		
		Optional<Usuarios> optinalUsuario = usuariosRepository.findByCorreo(correo);
		
		if (optinalUsuario.isPresent()) {
			
			Usuarios usuario = optinalUsuario.get();
			
			String passwordNew = generarStringAleatorio(6);
			
			usuario.setPassword(passwordNew);
			usuario.setConfigCompletada(Boolean.FALSE);
			usuariosRepository.save(usuario);
			
			gmailService.sendNewMail(correo, "Nueva contraseña para reestablecerla a su preferencia", 
					"Tu contraseña temporal para re-ingresar a la App del Acilo es: ".concat(passwordNew) );
			
			return BaseResponse.builder()
	                .code("1")
	                .messages("Contraseña reestablecida, se enviara a tu correo la nueva contraseña temporal para realizar el cambio")
	                .build();
		}
		
		
		return BaseResponse.builder()
                .code("-1")
                .messages("No se encontro ningun registro con el correo indicado")
                .build();
	}

	@Override
	public BaseResponse changePassword(final CambioPasswordRequest cambioPasswordRequest) {
		
		Optional<Usuarios> optinalUsuario = usuariosRepository.findByCorreoAndPassword(cambioPasswordRequest.getCorreo(),
				cambioPasswordRequest.getPassword());
		
		if (optinalUsuario.isPresent()) {
			
			Usuarios usuario = optinalUsuario.get();
			
			usuario.setConfigCompletada(Boolean.TRUE);
			usuario.setPassword(cambioPasswordRequest.getPasswordNew());
			usuariosRepository.save(usuario);
			
			return BaseResponse.builder()
	                .code("1")
	                .messages("Cambio de contraseña realizado correctamente")
	                .build();
		}
		
		return BaseResponse.builder()
                .code("-1")
                .messages("Contraseña temporal incorrecta, verificar de nuevo por favor")
                .build();
	}
	
	private String generarStringAleatorio(int longitud) {
        StringBuilder builder = new StringBuilder(longitud);
        SecureRandom random = new SecureRandom();

        random.ints(longitud, 0, (CARACTERES_DISPONIBLES.length - 1))
                .mapToObj(valor -> CARACTERES_DISPONIBLES[valor])
                .forEach(builder::append);

        return builder.toString();
    }

}