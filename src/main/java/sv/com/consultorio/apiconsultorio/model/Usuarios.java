package sv.com.consultorio.apiconsultorio.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@NoArgsConstructor
public class Usuarios implements Serializable {
	
	@Getter
	private static final long serialVersionUID = -5525292727362179234L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Rol rol; 

    private String correo;

    private String password;

    @Column(name ="ACTIVO")
    private boolean isActivo;
    
    @Column(name ="CONFIG_COMPLETADA")
    private boolean configCompletada;

}
