package sv.com.consultorio.apiconsultorio.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sv.com.consultorio.apiconsultorio.model.Doctores;

public interface DoctoresRepository extends JpaRepository<Doctores, Long> {
	
	@Query("SELECT d FROM Doctores d WHERE (:dui is null or UPPER(d.dui) like '%' || :dui || '%') ")
    Page<Doctores> findLikeByDui(@Param("dui") String dui, Pageable pageable);
	
	Optional<Doctores> findByDui(String dui);

}