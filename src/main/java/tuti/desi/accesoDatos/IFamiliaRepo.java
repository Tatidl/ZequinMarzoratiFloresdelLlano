package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Familia;

public interface IFamiliaRepo extends JpaRepository<Familia, Long> {
}
