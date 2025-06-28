package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tuti.desi.entidades.EntregaAsistencia;

import java.time.LocalDate;
import java.util.List;

public interface IEntregaAsistenciaRepo extends JpaRepository<EntregaAsistencia, Long> {

    boolean existsByAsistido_Familia_NroFamiliaAndFecha(Long nroFamilia, LocalDate fecha);

    List<EntregaAsistencia> findByFecha(LocalDate fecha);

    @Query("""
            select e
            from EntregaAsistencia e
            where (:fecha is null or e.fecha = :fecha)
            and (:nroFamilia is null or e.asistido.familia.nroFamilia = :nroFamilia)
            and (:nombreFamilia is null
                 or lower(e.asistido.familia.nombre) like lower(concat('%', :nombreFamilia, '%')))
            order by e.fecha desc
            """)
    List<EntregaAsistencia> buscar(LocalDate fecha, Long nroFamilia, String nombreFamilia);
}
