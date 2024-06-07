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
@Table(name = "CITAS")
@Getter
@Setter
@NoArgsConstructor
public class Citas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "FECHA_REGISTRO" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaRegistro;
	
	@Column(name = "FECHA_CITA" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaCita;
	
	@Column(name = "FECHA_FIN" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaFin;
	
	@Column(name = "FECHA_REPROGRAMACION" )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaReprogramacion;
	
	
	private String origen;
	
	private String tratamiento;
	
	private String medicinas;
	
	@Column(name = "comentarios_adicionales" )
	private String comentariosAdicionales;
	
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Paciente paciente;
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Doctores doctor;
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_CONSULTORIO", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Consultorios consultorio;
	
	
	@Getter(onMethod = @__(@JsonIgnore))
	@JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private EstadosCitas estadoCita;

}