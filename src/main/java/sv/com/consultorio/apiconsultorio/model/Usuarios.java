package sv.com.consultorio.apiconsultorio.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@NoArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Rol rol; 

    private String correo;

    private String password;

    @Column(name ="ACTIVO")
    private boolean isActivo;

}
