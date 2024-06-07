package sv.com.consultorio.apiconsultorio.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class DoctoresRequest {

	
	private String correo; //only create
	private String dui;
	private String nombres;
	private String apellidos;
	private String telefono;
	private Long especialidadId;
	
}