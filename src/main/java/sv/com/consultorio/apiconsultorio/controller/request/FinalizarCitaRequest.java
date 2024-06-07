package sv.com.consultorio.apiconsultorio.controller.request;

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
public class FinalizarCitaRequest {
	
	private String origen;
	
	private String tratamiento;
	
	private String medicinas;
	
}
