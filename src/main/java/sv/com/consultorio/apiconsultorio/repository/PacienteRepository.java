package sv.com.consultorio.apiconsultorio.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sv.com.consultorio.apiconsultorio.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	@Query("SELECT p FROM Paciente p WHERE (:dui is null or UPPER(p.dui) like '%' || :dui || '%') ")
    Page<Paciente> findLikeByDui(@Param("dui") String dui, Pageable pageable);
	
	Optional<Paciente> findByDui(String dui);

}