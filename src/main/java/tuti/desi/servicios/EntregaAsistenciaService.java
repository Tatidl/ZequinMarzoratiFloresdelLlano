package tuti.desi.servicios;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tuti.desi.presentacion.entregas.EntregaForm;
import tuti.desi.presentacion.entregas.EntregaResumenDTO;
import tuti.desi.presentacion.entregas.EntregasBuscarForm;

public interface EntregaAsistenciaService {
    EntregaForm alta(EntregaForm entregaForm);
    void baja(Long id);
    Page<EntregaResumenDTO> listar(EntregasBuscarForm form, Pageable pageable);
}