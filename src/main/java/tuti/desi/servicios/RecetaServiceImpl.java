package tuti.desi.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.accesoDatos.IIngredienteRepo;
import tuti.desi.accesoDatos.IRecetaRepo;
import tuti.desi.entidades.Ingrediente;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.recetas.RecetaForm;
import tuti.desi.presentacion.recetas.RecetaResumenDTO;
import tuti.desi.util.RecetaMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

    private final IRecetaRepo recetaRepo;
    private final IIngredienteRepo ingredienteRepo;

    private Ingrediente getIngrediente(Long id) {
        return ingredienteRepo.findById(id).orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));
    }

    //----------------------------------
    @Override
    public RecetaForm alta(RecetaForm form) {
        if (recetaRepo.existsByNombreIgnoreCaseAndActivaTrue(form.getNombre())) {
            throw new Excepcion("Ya existe una receta con ese nombre");
        }
        var entidad = RecetaMapper.aEntidad(form, this::getIngrediente);
        recetaRepo.save(entidad);
        form.setId(entidad.getId());
        return form;
    }

    @Override
    public RecetaForm editar(Long id, RecetaForm form) {
        var receta = recetaRepo.findById(id).orElseThrow(() -> new Excepcion("Receta no encontrada"));
        RecetaMapper.fusionar(receta, form, this::getIngrediente);
        return form;
    }

    @Override
    public void baja(Long id) {
        var receta = recetaRepo.findById(id).orElseThrow(() -> new Excepcion("Receta no encontrada"));
        receta.setActiva(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecetaResumenDTO> listar(String nombre, Integer minCal, Integer maxCal, Pageable pageable) {
        return recetaRepo.filtrar(nombre, minCal, maxCal, pageable)
                .map(r -> new RecetaResumenDTO(r.getId(), r.getNombre(), r.getCaloriasTotales()));
    }
}