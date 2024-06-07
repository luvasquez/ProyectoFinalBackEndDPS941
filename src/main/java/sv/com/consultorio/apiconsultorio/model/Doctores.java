package sv.com.consultorio.apiconsultorio.model;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "DOCTORES")
@Getter
@Setter
@NoArgsConstructor
public class Doctores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true, name = "DUI", length = 10)
	private String dui;
	
	@Column(name = "NOMBRES" )
	private String nombres;
	
	@Column(name = "APELLIDOS" )
	private String apellidos;
	
	@Column(name = "TELEFONO" )
	private String telefono;
	
	@Column(name ="ACTIVO")
    private boolean isActivo;
	
	@Column(name = "FECHA_REGISTRO" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaRegistro;
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Usuarios usuario;
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_ESPECIALIDAD", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Especialidades especialidad;
	
	
	public String getNombreCompleto () {
		return this.nombres.concat(" ").concat(apellidos);
	}
	
}