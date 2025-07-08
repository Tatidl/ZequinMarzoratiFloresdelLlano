package tuti.desi.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tuti.desi.entidades.Preparacion;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.preparaciones.PreparacionForm;
import tuti.desi.presentacion.preparaciones.PreparacionResumenDTO;

import java.time.LocalDate;
import java.util.List;

public interface PreparacionService {
    PreparacionForm alta(PreparacionForm form);
    PreparacionForm editarFecha(Long id, PreparacionForm form);
    void baja(Long id);
    Page<PreparacionResumenDTO> listar(String nombreReceta, LocalDate fecha, Pageable pageable);
    PreparacionForm obtener(Long id);
}
