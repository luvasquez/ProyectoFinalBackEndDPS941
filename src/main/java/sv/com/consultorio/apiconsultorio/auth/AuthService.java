package sv.com.consultorio.apiconsultorio.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sv.com.consultorio.apiconsultorio.jwt.JwtService;
import sv.com.consultorio.apiconsultorio.model.Usuario;

@Service
@RequiredArgsConstructor
public class AuthService {
/*
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), encodedPassword));
        UserDetails user = null;
        if("admin$".equals(request.getUsername())) {
            user = Usuario.builder()
                    .username("admin$")
                    .password("password")
                    .build();
        } else {
            new UsernameNotFoundException("User not fournd");
        }

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
*/
}
