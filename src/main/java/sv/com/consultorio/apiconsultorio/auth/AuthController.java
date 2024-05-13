package sv.com.consultorio.apiconsultorio.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;
import sv.com.consultorio.apiconsultorio.service.JwtUtilService;

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
        
        final String codigoRol = usuariosRepository.findCodigoRolByCorreo(request.getEmail());
        
    
        return ResponseEntity.ok(new AuthResponse(request.getEmail(),codigoRol, jwt));
    }


}
