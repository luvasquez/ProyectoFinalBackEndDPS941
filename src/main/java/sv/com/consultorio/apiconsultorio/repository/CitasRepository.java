package sv.com.consultorio.apiconsultorio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sv.com.consultorio.apiconsultorio.model.Citas;

public interface CitasRepository extends JpaRepository<Citas, Long> {
	
	
	@Query("SELECT c FROM Citas c WHERE c.estadoCita.codigo IN :estados"
			+ " AND ( CAST(c.fechaCita AS date) = CAST(:fecha AS date) ) ")
	Page<Citas> findProgramadasReprogramadasLikeByFechaCita(@Param("estados") List<String> estados ,@Param("fecha") LocalDate fecha, Pageable pageable);
	
	@Query("SELECT c FROM Citas c WHERE c.estadoCita.codigo IN :estados ")
	Page<Citas> findProgramadasReprogramadas(@Param("estados") List<String> estados, Pageable pageable);
	
}