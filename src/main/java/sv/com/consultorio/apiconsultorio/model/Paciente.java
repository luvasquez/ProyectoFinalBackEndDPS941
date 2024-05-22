package sv.com.consultorio.apiconsultorio.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PACIENTES")
@Getter
@Setter
@NoArgsConstructor
public class Paciente implements Serializable {
	
	@Getter
	private static final long serialVersionUID = -4595940225227920868L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true, name = "DUI", length = 10)
	private String dui;
	
	@Column(name = "NOMBRES" )
	private String nombres;
	
	@Column(name = "APELLIDOS" )
	private String apellidos;
	
	@Column(name = "FECHA_NACIMIENTO" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaNacimiento;
	
	@Column(name = "GENERO" )
	private String genero;
	
	@Column(name = "TIPO_SANGRE" )
	private String tipoSangre;
	
	@Column(name = "PESO" , scale = 2, precision = 6 )
    private BigDecimal peso;
	
	@Column(name = "ALTURA" , scale = 2, precision = 4 )
    private BigDecimal altura;
	
	@Column(name = "TELEFONO" )
	private String telefono;
	
	@Column(name = "DIRECCION" )
	private String direccion;
	
	@Column(name = "FECHA_REGISTRO" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaRegistro;
	
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Usuarios usuario; 

}