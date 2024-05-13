package sv.com.consultorio.apiconsultorio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sv.com.consultorio.apiconsultorio.auth.AuthController;
import sv.com.consultorio.apiconsultorio.model.Usuarios;
import sv.com.consultorio.apiconsultorio.repository.UsuariosRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @NonNull
    private final UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = getById(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    public Usuario getById(String username) {

        Optional<Usuarios> userRegisterd = usuariosRepository.findByCorreo(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userRegisterd.isPresent()) {
            Usuarios registered = userRegisterd.get();
            Usuario usuario = new Usuario(registered.getCorreo(),
                    encoder.encode(registered.getPassword()),
                    Set.of("ADMIN"));

            return usuario;
        }

        return null;

        /*// "secreto" => [BCrypt] => "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq"
        var password = "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq";
        Usuario juan = new Usuario(
                "jcabelloc",
                password,
                Set.of("USER")
        );

        Usuario maria = new Usuario(
                "mlopez",
                password,
                Set.of("ADMIN")
        );
        var usuarios = List.of(juan, maria);

        return usuarios
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);*/
    }
}
