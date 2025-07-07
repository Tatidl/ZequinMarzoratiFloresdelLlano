package tuti.desi.accesoDatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Familia;

public interface IFamiliaRepo extends JpaRepository<Familia, Long> {
    boolean existsByNroFamiliaAndActivaTrue(Integer nroFamilia);
    Page<Familia> findByActivaTrueAndNombreContainingIgnoreCase(String filtro, Pageable pageable);
    Familia findByNroFamiliaAndActivaTrue(Integer nroFamilia);
}