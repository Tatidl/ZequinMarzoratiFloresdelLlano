package tuti.desi.accesoDatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tuti.desi.entidades.Receta;

import java.util.List;

public interface IRecetaRepo extends JpaRepository<Receta,Long> {
    boolean existsByNombreIgnoreCaseAndActivaTrue(String nombre);

    @Query("""
    SELECT r
    FROM Receta r
    WHERE r.activa = true
    AND (:nombre IS NULL OR UPPER(r.nombre) LIKE UPPER(CONCAT('%', :nombre, '%')))
    AND (:minCal IS NULL OR r.caloriasTotales >= :minCal)
    AND (:maxCal IS NULL OR r.caloriasTotales <= :maxCal)
    """)
    Page<Receta> filtrar(String nombre, Integer minCal, Integer maxCal, Pageable pageable);
}
