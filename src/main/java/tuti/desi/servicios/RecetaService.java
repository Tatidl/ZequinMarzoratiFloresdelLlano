package tuti.desi.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tuti.desi.entidades.Receta;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.recetas.RecetaForm;
import tuti.desi.presentacion.recetas.RecetaResumenDTO;

import java.util.List;

public interface RecetaService {
    RecetaForm alta(RecetaForm form);
    RecetaForm editar(Long id, RecetaForm form);
    void baja(Long id);
    Page<RecetaResumenDTO> listar(String nombre, Integer minCal, Integer maxCal, Pageable pageable);
}
