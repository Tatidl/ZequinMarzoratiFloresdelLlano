package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Preparacion;

public interface IPreparacionRepo extends JpaRepository<Preparacion, Long> {
}
