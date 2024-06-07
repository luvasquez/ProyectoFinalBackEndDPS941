package sv.com.consultorio.apiconsultorio.controller.request;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sv.com.consultorio.apiconsultorio.config.JsonLocalDateDeserializer;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegistroCitaRequest {
	
	private Long pacienteId;
	
	private Long doctorId;
	
	private Long consultorioId;
	
	@JsonDeserialize(using = JsonLocalDateDeserializer.class)  
	private LocalDate fechaCita;

}