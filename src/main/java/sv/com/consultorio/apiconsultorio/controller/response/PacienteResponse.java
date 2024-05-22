package sv.com.consultorio.apiconsultorio.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponse {
	
	private Long pacienteId;
	private Long usuarioId;
	
	private String dui;
	private String nombres;
	private String apellidos;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaNacimiento;
	
	private String genero;
	private String tipoSangre;
	private BigDecimal peso;
	private BigDecimal altura;
	private String telefono;
	private String direccion;

}