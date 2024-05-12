package sv.com.consultorio.apiconsultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.com.consultorio.apiconsultorio.model.Especialidades;

@Repository
public interface EspecialidadesRepository extends JpaRepository<Especialidades, Long> {
}
