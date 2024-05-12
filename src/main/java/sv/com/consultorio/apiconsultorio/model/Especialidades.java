package sv.com.consultorio.apiconsultorio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ESPECIALIDADES")
@Getter
@Setter
@NoArgsConstructor
public class Especialidades implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String descripcion;

    @Column(name ="FECHA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaDeRegistro;

}
