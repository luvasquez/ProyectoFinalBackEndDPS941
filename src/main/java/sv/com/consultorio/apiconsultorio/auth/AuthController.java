package sv.com.consultorio.apiconsultorio.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import sv.com.consultorio.apiconsultorio.controller.response.BaseResponse;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.JwtUtilService;
import sv.com.consultorio.apiconsultorio.service.UsuariosService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final UserDetailsService userDetailsService;

    @NonNull
    private final JwtUtilService jwtUtilService;
    
    @NonNull
    private final UsuariosRepository usuariosRepository;
    
    @NonNull
    private final UsuariosService usuariosService;
    
    //@Value("${spring.security.oauth2.client.registration.google.client-id}")
    //private final String googleClientId;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        logger.info("Autenticando al usuario {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()));
        

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                request.getEmail());

        final String jwt = jwtUtilService.generateToken(userDetails);
        
        Usuarios usuario = usuariosRepository.findUsuarioByCorreo(request.getEmail());  
    
        return ResponseEntity.ok(
        		AuthResponse.builder().correo(request.getEmail())
        		.codigoRol(usuario.getRol().getCodigo())
        		.active(usuario.isActivo())
        		.configCompletada(usuario.isConfigCompletada())
        		.token(jwt)
        		.build());
    }
    
    /**
     * Sirve para poder reiniciar la contraseña (envio de correo con nueva contraseña)
     * @param correo
     * @return
     */
    @PutMapping("/resetPassword/{correo}")
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse resetPassword (@PathVariable String correo) {
		return usuariosService.resetPassword(correo);
	}
    
    @PostMapping("/OAuth2/Google/")
    public ResponseEntity<AuthResponse> LoginGoogle(@RequestBody LoginGoogleRequest request) {
    	
    	String email = null;
    	
    	
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
    	        .setAudience(Collections.singletonList("746095693020-mqsfdcfgh224s00ktqhvfcfqcva2br0t.apps.googleusercontent.com"))
    	        .build();
        
    	try {
			GoogleIdToken idTokenObj = verifier.verify(request.getToken());
			if (idTokenObj != null) {
			      Payload payload = idTokenObj.getPayload();
			      String userId = payload.getSubject();
			      email = payload.getEmail();
			      
			    } else {
			      return null;
			    }
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
    	
    	Usuarios usuarioLogin = usuariosRepository.findUsuarioByCorreo(email);
    	
    	
    	
    	logger.info("Autenticando al usuario {}", usuarioLogin.getCorreo());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLogin.getCorreo(),
                		usuarioLogin.getPassword()));
        

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
        		usuarioLogin.getCorreo());

        final String jwt = jwtUtilService.generateToken(userDetails);
        
        Usuarios usuario = usuariosRepository.findUsuarioByCorreo(usuarioLogin.getCorreo());  
    
        return ResponseEntity.ok(
        		AuthResponse.builder().correo(usuarioLogin.getCorreo())
        		.codigoRol(usuario.getRol().getCodigo())
        		.active(usuario.isActivo())
        		.configCompletada(usuario.isConfigCompletada())
        		.token(jwt)
        		.build());
    }
    
    

}
