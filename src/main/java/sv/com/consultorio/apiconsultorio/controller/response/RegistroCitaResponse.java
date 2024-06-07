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
public class RegistroCitaResponse {

	private Long citaId;
	
	private Long pacienteId;
	
	private Long doctorId;
	
	private Long consultorioId;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaCita;
	
}