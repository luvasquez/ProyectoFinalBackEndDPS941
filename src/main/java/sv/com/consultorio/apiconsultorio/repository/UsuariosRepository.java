package sv.com.consultorio.apiconsultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.com.consultorio.apiconsultorio.model.Usuarios;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByCorreo(String correo);
    
    @Query("SELECT x.rol.codigo FROM Usuarios x WHERE x.correo=:correo")
    String findCodigoRolByCorreo( @Param("correo") String correo);
    
    @Query("SELECT u FROM Usuarios u WHERE (:correo is null or UPPER(u.correo) like '%' || :correo || '%') ")
    List<Usuarios> findLikeCorreo(@Param("correo") String correo);

}
