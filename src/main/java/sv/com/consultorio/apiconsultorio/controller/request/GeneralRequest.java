package sv.com.consultorio.apiconsultorio.controller.request;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GeneralRequest {
    private Long id;
    private String codigo;
    private String descripcion;
}
