package sv.com.consultorio.apiconsultorio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CONSULTORIOS")
@Getter
@Setter
@NoArgsConstructor
public class Consultorios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String descripcion;

    @Column(name ="FECHA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaDeRegistro;


}
