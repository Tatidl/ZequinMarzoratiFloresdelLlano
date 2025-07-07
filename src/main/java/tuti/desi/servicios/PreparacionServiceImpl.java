package tuti.desi.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.accesoDatos.IPreparacionRepo;
import tuti.desi.accesoDatos.IProductoRepo;
import tuti.desi.accesoDatos.IRecetaRepo;
import tuti.desi.entidades.Preparacion;
import tuti.desi.entidades.Producto;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.preparaciones.PreparacionForm;
import tuti.desi.presentacion.preparaciones.PreparacionResumenDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class PreparacionServiceImpl  implements PreparacionService {

    private final IPreparacionRepo preparacionRepo;
    private final IRecetaRepo recetaRepo;
    private final IProductoRepo productoRepo;

    //---------------------------------
    @Override
    public PreparacionForm alta(PreparacionForm form) {
        validarAlta(form);
        var receta = recetaRepo.findById(form.getIdReceta())
                .orElseThrow(() -> new Excepcion("Receta no encontada"));

        var preparacion = new Preparacion();
        preparacion.setFechaCoccion(form.getFecha());
        preparacion.setReceta(receta);
        preparacion.setTotalRacionesPreparadas(form.getRaciones());
        preparacion.setStockRacionesRestantes(form.getRaciones());
        preparacion.setActiva(true);

        // Verificar stock y descontar
        receta.getItems().forEach(item -> {
            var ing = item.getIngrediente();
            if (ing instanceof Producto prod) {
                double requerido = item.getCantidadKg().multiply(new BigDecimal(form.getRaciones())).doubleValue();
                if (prod.getStockDisponible() < requerido) {
                    throw new Excepcion("Stock insuficiente del ingrediente " + prod.getNombre());
                }
                prod.setStockDisponible((float) (prod.getStockDisponible() - requerido));
                productoRepo.save(prod);
            }
        });
        preparacionRepo.save(preparacion);
        form.setId(preparacion.getId());
        return form;
    }

    @Override
    public PreparacionForm editarFecha(Long id, PreparacionForm form) {
        var preparacion = preparacionRepo.findById(id).orElseThrow(() -> new Excepcion("Preparación no encontrada"));
        if (!preparacion.isActiva()) throw new Excepcion("Preparación eliminada");
        if (form.getFecha().isAfter(LocalDate.now())) throw new Excepcion("La fecha no puede ser futura");
        if (preparacionRepo.existsByRecetaIdAndFechaCoccionAndActivaTrue(preparacion.getReceta().getId(), preparacion.getFechaCoccion())) {
            throw new Excepcion("Ya existe una preparación de esa receta para la fecha indicada");
        }
        preparacion.setFechaCoccion(form.getFecha());
        return form;
    }

    @Override
    public void baja(Long id) {
        var preparacion = preparacionRepo.findById(id).orElseThrow(() -> new Excepcion("Preparación no encontrada"));
        if (!preparacion.isActiva()) return;
        var receta = preparacion.getReceta();
        receta.getItems().forEach(item -> {
            var ing = item.getIngrediente();
            if (ing instanceof Producto prod) {
                double devolver = item.getCantidadKg().multiply(new BigDecimal(preparacion.getTotalRacionesPreparadas())).doubleValue();
                prod.setStockDisponible((float) (prod.getStockDisponible() + devolver));
                productoRepo.save(prod);
            }
        });
        preparacion.setActiva(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreparacionResumenDTO> listar(String nombreReceta, LocalDate fecha, Pageable pageable) {
        return preparacionRepo.findAll(pageable).map(p -> new PreparacionResumenDTO(
                p.getId(),
                p.getFechaCoccion(),
                p.getReceta().getNombre(),
                p.getTotalRacionesPreparadas(),
                p.getReceta().getCaloriasTotales()
        ));
    }

    // Auxiliar
    private void validarAlta(PreparacionForm form) {
        if (form.getFecha().isAfter(LocalDate.now())) {
            throw new Excepcion("La fecha no puede ser futura");
        }
        if (preparacionRepo.existsByRecetaIdAndFechaCoccionAndActivaTrue(form.getIdReceta(), form.getFecha())) {
            throw new Excepcion("Ya existe una preparación de esa receta para la fecha indicada");
        }
    }


}