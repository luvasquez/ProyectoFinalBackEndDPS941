package sv.com.consultorio.apiconsultorio.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.JwtUtilService;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class CheckTokenController {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckTokenController.class);
	
	@NonNull
    private final JwtUtilService jwtUtilService;
	
	@NonNull
    private final UserDetailsService userDetailsService;
	
	@NonNull
    private final UsuariosRepository usuariosRepository;
	
	@GetMapping("/token")
	public ResponseEntity<AuthResponse> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		
		logger.info("checkToken al usuario {}", token);
		
		if (token != null && !"".equals(token))
			token = token.substring(7);
		
		final String email = jwtUtilService.extractUsername(token);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		final String jwt = jwtUtilService.generateToken(userDetails);
        
		Usuarios usuario = usuariosRepository.findUsuarioByCorreo(email);  
	    
        return ResponseEntity.ok(
        		AuthResponse.builder().correo(email)
        		.codigoRol(usuario.getRol().getCodigo())
        		.active(usuario.isActivo())
        		.configCompletada(usuario.isConfigCompletada())
        		.token(jwt)
        		.build());
	}

}
