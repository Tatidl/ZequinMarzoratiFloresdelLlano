package tuti.desi.accesoDatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tuti.desi.entidades.EntregaAsistencia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEntregaAsistenciaRepo extends JpaRepository<EntregaAsistencia, Long> {
    boolean existsByFamiliaIdAndFechaAndActivaTrue(Long idFamilia, LocalDate fecha);
    @Query("""
        SELECT e
        FROM EntregaAsistencia e
        WHERE e.activa = true
        AND (:fecha IS NULL OR e.fecha = :fecha)
        AND (:nroFamilia IS NULL OR e.familia.nroFamilia = :nroFamilia)
        AND (:nombre IS NULL OR UPPER(e.familia.nombre) LIKE UPPER(CONCAT('%', :nombre, '%')))
    """)
    Page<EntregaAsistencia> filtrar(LocalDate fecha, Integer nroFamilia, String nombre, Pageable pageable);
}