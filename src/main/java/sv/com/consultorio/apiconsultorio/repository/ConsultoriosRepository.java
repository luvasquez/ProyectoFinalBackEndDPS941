package sv.com.consultorio.apiconsultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sv.com.consultorio.apiconsultorio.model.Consultorios;

@Repository
public interface ConsultoriosRepository extends JpaRepository<Consultorios, Long> {
}
