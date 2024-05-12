package sv.com.consultorio.apiconsultorio.controller.response;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EstadosCitasResponse {

    String codigo;

    String descripcion;

}
