package sv.com.consultorio.apiconsultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.com.consultorio.apiconsultorio.model.Rol;


public interface RolRepository extends JpaRepository<Rol, Long> {

	Rol findByCodigo(String codigo);
	
}