package sv.com.consultorio.apiconsultorio.controller.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse {
    private Long id;

    String codigo;

    String descripcion;

    Date fechaDeRegistro;
}
