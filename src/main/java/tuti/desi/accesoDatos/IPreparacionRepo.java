package tuti.desi.accesoDatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tuti.desi.entidades.Preparacion;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPreparacionRepo extends JpaRepository<Preparacion, Long> {
    boolean existsByRecetaIdAndFechaCoccionAndActivaTrue(Long idReceta, LocalDate fecha);
    List<Preparacion> findByFechaCoccionAndActivaTrueAndStockRacionesRestantesGreaterThan(LocalDate fecha, int stockRaciones);
    @Query("SELECT p FROM Preparacion p WHERE p.activa = true AND (:fecha IS NULL OR p.fechaCoccion = :fecha) AND (:nombreReceta IS NULL OR p.receta.nombre LIKE CONCAT('%', :nombreReceta, '%'))")
    Page<Preparacion> findByActivaTrueAndFechaAndRecetaNombreContaining(LocalDate fecha, String nombreReceta, Pageable pageable);
}