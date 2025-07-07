package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Asistido;

public interface IAsistidoRepo extends JpaRepository<Asistido, Long> {
}