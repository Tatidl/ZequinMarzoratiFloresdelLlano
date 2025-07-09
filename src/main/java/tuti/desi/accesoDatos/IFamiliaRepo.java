package tuti.desi.accesoDatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tuti.desi.entidades.Familia;

public interface IFamiliaRepo extends JpaRepository<Familia, Long> {
    boolean existsByNroFamiliaAndActivaTrue(Integer nroFamilia);

    Familia findByNroFamiliaAndActivaTrue(Integer nroFamilia);

    @Query("SELECT f FROM Familia f WHERE f.activa = true AND (?1 IS NULL OR f.nroFamilia = ?1) AND (?2 IS NULL OR f.nombre LIKE %?2%)")
    Page<Familia> findByActivaTrueAndNroFamiliaAndNombre(Integer nroFamilia, String nombre, Pageable pageable);
}