package tuti.desi.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.accesoDatos.IEntregaAsistenciaRepo;
import tuti.desi.accesoDatos.IFamiliaRepo;
import tuti.desi.accesoDatos.IPreparacionRepo;
import tuti.desi.accesoDatos.IVoluntarioRepo;
import tuti.desi.entidades.*;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.entregas.EntregaForm;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntregaAsistenciaServiceImpl implements EntregaAsistenciaService {

    private final IEntregaAsistenciaRepo repo;
    private final IFamiliaRepo familiaRepo;
    private final IPreparacionRepo preparacionRepo;
    private final IVoluntarioRepo voluntarioRepo;

    @Override
    @Transactional
    public EntregaAsistencia alta(EntregaForm form, Long idVoluntario) throws Excepcion {

        LocalDate hoy = LocalDate.now();

        // 1. Validar familia
        Familia familia = familiaRepo.findById(form.getNroFamilia())
                .orElseThrow(() -> new Excepcion("Familia Inexistente"));

        // 2. Una entrega por día y por familia
        if (repo.existsByAsistido_Familia_NroFamiliaAndFecha(familia.getNroFamilia(), hoy)) {
            throw new Excepcion("Ya existe una entrega registrada para hoy");
        }

        // 3. Validar preparación y stock
        Preparacion prep = preparacionRepo.findById(form.getPreparacionId())
                .orElseThrow(() -> new Excepcion("Plato/preparación inexistente"));

        if (prep.getStockRacionesRestantes() < form.getCantidadRaciones()) {
            throw new Excepcion("Stock insuficiente (" + prep.getStockRacionesRestantes() + ")");
        }

        // 4. No más raciones que integrantes
        int integrantes = familia.getIntegrantes().size();
        if (form.getCantidadRaciones() > integrantes) {
            throw new Excepcion("No puede entregar más raciones (" + form.getCantidadRaciones() + ") que integrantes (" + integrantes + ")");
        }

        // 5. Voluntario
        Voluntario voluntario = voluntarioRepo.findById(idVoluntario)
                .orElseThrow(() -> new Excepcion("Voluntario inexistente"));

        // 6. Chequeamos que la familia tenga integrantes
        Asistido asistido = familia.getIntegrantes().stream()
                .findFirst()
                .orElseThrow(() -> new Excepcion("La familia no tiene integrantes cargados"));

        // 7. Finalmente guardamos en base de datos
        EntregaAsistencia entrega = EntregaAsistencia.builder()
                .fecha(hoy)
                .cantidadRaciones(form.getCantidadRaciones())
                .preparacion(prep)
                .asistido(asistido)
                .voluntario(voluntario)
                .build();

        // descuento stock
        prep.setStockRacionesRestantes(prep.getStockRacionesRestantes() - form.getCantidadRaciones());

        return repo.save(entrega);
    }

    @Override
    @Transactional
    public void baja(Long idEntrega) throws Excepcion {
        EntregaAsistencia entrega = repo.findById(idEntrega)
                .orElseThrow(() -> new Excepcion("Entrega inexistente"));

        // devolver stock
        Preparacion prep = entrega.getPreparacion();
        prep.setStockRacionesRestantes(prep.getStockRacionesRestantes() + entrega.getCantidadRaciones());

        repo.delete(entrega);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntregaAsistencia> listar(LocalDate fecha, Long nroFamilia, String nombreFamilia) {
        return repo.buscar(fecha, nroFamilia, nombreFamilia);
    }
}