package sv.com.consultorio.apiconsultorio.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String correo;
	private String codigoRol;
	
    String token;
}