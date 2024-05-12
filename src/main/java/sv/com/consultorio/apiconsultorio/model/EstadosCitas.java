package sv.com.consultorio.apiconsultorio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ESTADOS_CITA")
@Getter
@Setter
@NoArgsConstructor
public class EstadosCitas {

    @Id
    private String codigo;

    private String descripcion;

}
