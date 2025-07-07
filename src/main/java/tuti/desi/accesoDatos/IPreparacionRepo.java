package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Preparacion;

import java.time.LocalDate;

public interface IPreparacionRepo extends JpaRepository<Preparacion, Long> {
    boolean existsByRecetaIdAndFechaCoccionAndActivaTrue(Long idReceta, LocalDate fecha);
}