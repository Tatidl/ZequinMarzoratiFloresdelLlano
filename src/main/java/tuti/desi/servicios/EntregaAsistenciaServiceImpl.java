package tuti.desi.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.accesoDatos.IEntregaAsistenciaRepo;
import tuti.desi.accesoDatos.IFamiliaRepo;
import tuti.desi.accesoDatos.IPreparacionRepo;
import tuti.desi.entidades.Asistido;
import tuti.desi.entidades.EntregaAsistencia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.entregas.EntregaForm;
import tuti.desi.presentacion.entregas.EntregaResumenDTO;
import tuti.desi.presentacion.entregas.EntregasBuscarForm;
import tuti.desi.util.EntregaMapper;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class EntregaAsistenciaServiceImpl implements EntregaAsistenciaService {

    private final IEntregaAsistenciaRepo entregaAsistenciaRepo;
    private final IFamiliaRepo familiaRepo;
    private final IPreparacionRepo preparacionRepo;
    private final IEntregaAsistenciaRepo iEntregaAsistenciaRepo;

    //-----------------
    @Override
    public EntregaForm alta(EntregaForm entregaForm) {
        var fechaHoy = LocalDate.now();

        var familia = familiaRepo.findByNroFamiliaAndActivaTrue(entregaForm.getNroFamilia());

        var preparacion = preparacionRepo.findById(entregaForm.getPreparacionId())
                .orElseThrow(() -> new Excepcion("Preparación no encontrada"));

        if (iEntregaAsistenciaRepo.existsByFamiliaIdAndFechaAndActivaTrue(familia.getId(), fechaHoy))
            throw new Excepcion("Ya se registró una entrega hoy para la familia");

        // Validar raciones e integrantes
        var integrantesActivos = (int) familia.getIntegrantes().stream().filter(Asistido::isActivo).count();
        if (entregaForm.getCantidadRaciones() > integrantesActivos)
            throw new Excepcion("No se pueden entregar más raciones que integrantes activos");

        // Validar stock
        if (preparacion.getStockRacionesRestantes() < entregaForm.getCantidadRaciones())
            throw new Excepcion("Stock insuficiente de la preparación");

        // Descontar stock y guardar
        preparacion.setStockRacionesRestantes(preparacion.getStockRacionesRestantes() - entregaForm.getCantidadRaciones());

        var entrega = new EntregaAsistencia();
        entrega.setFamilia(familia);
        entrega.setPreparacion(preparacion);
        entrega.setCantidadRaciones(entregaForm.getCantidadRaciones());
        entrega.setFecha(fechaHoy);
        entrega.setActiva(true);
        entregaAsistenciaRepo.save(entrega);

        entregaForm.setId(entrega.getId());
        entregaForm.setFechaEntrega(fechaHoy);
        return entregaForm;
    }

    @Override
    public void baja(Long id) {
        var entrega = entregaAsistenciaRepo.findById(id).orElseThrow(() -> new Excepcion("Entrega no encontrada"));
        if (!entrega.isActiva()) return;
        // Revierto el stock
        var preparacion = entrega.getPreparacion();
        preparacion.setStockRacionesRestantes(preparacion.getStockRacionesRestantes() + entrega.getCantidadRaciones());
        entrega.setActiva(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntregaResumenDTO> listar(EntregasBuscarForm form, Pageable pageable) {
        return entregaAsistenciaRepo.filtrar(form.getFecha(), form.getNroFamilia(), form.getNombre(), pageable)
                .map(EntregaMapper::aDTO);
    }
}