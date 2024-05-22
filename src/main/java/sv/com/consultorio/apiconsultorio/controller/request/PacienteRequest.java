package sv.com.consultorio.apiconsultorio.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Future;
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
public class PacienteRequest {

	
	private String dui;
	private String nombres;
	private String apellidos;
	
	//@Future(message = "La fechaEntrega debe ser posterior a la fecha presente")
	@JsonDeserialize(using = JsonLocalDateDeserializer.class)  
	private LocalDate fechaNacimiento;
	
	private String genero;
	private String tipoSangre;
	
    private BigDecimal peso;
	
    //@NotNull(message = "No puede estar vacio el campo precioEnvio")
	//@Digits(integer = 10, fraction = 2, message = "El campo precioEnvio soporta un total de 8 numeros enteros y 2 en su parte decimal")
    private BigDecimal altura;
	
	private String telefono;
	private String direccion;
	
}