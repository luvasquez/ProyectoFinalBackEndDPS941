package sv.com.consultorio.apiconsultorio.controller.response;

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
public class CitasResponse {
	
	
	private Long citaId;
	
	private String paciente;
	
	private String doctor;
	
	private String consultorio;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechacita;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaReprogramada;

}