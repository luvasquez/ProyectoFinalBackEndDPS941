package sv.com.consultorio.apiconsultorio.controller.response;

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
public class UsuariosResponse {
	
	 private Long id;
	 private String rol;
	 private String correo;
	 private Boolean activo;
	 private Boolean configCompletada;

}