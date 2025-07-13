package tuti.desi.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tuti.desi.presentacion.familias.FamiliaForm;
import tuti.desi.presentacion.familias.FamiliaResumenDTO;

public interface FamiliaService {
    FamiliaForm alta(FamiliaForm form);
    FamiliaForm editar(Long id, FamiliaForm form);
    void baja (Long id);
    Page<FamiliaResumenDTO> listar(String filtro, Pageable pageable);
    FamiliaForm obtenerParaEdicion(Long id);
    void eliminarIntegrante(Integer nroFamilia, Long id);
}