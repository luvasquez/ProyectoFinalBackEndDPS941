package sv.com.consultorio.apiconsultorio.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
public class Rol implements Serializable {
	
	@Getter
	private static final long serialVersionUID = 6056141450587273524L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String descripcion;
	
}