package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Voluntario;

public interface IVoluntarioRepo extends JpaRepository<Voluntario, Long> {
}