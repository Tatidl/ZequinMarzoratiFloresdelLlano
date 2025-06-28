package tuti.desi.servicios;

import tuti.desi.entidades.EntregaAsistencia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.entregas.EntregaForm;

import java.time.LocalDate;
import java.util.List;

public interface EntregaAsistenciaService {

    EntregaAsistencia alta(EntregaForm form, Long idVoluntario) throws Excepcion;
    void baja(Long idEntrega) throws Excepcion;
    List<EntregaAsistencia> listar(LocalDate fecha, Long nroFamilia, String nombreFamilia);
}
