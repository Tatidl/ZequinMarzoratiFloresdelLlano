package tuti.desi.presentacion.recetas;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.accesoDatos.IIngredienteRepo;
import tuti.desi.accesoDatos.IRecetaRepo;
import tuti.desi.entidades.ItemReceta;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.recetas.ItemRecetaForm;
import tuti.desi.presentacion.recetas.RecetaForm;
import tuti.desi.servicios.RecetaService;

@Controller
@RequestMapping("/recetas")
@RequiredArgsConstructor
public class RecetasRegistrarEditarController {

    private final RecetaService   recetaService;      // la lógica ya existente
    private final IRecetaRepo     recetaRepo;         // para traer la receta completa
    private final IIngredienteRepo ingredienteRepo;   // para la lista del combo

    /* ------------------------------------------------------------ */
    /** Carga la lista de ingredientes (entidades) al modelo. */
    private void cargarListaIngredientes(Model model){
        model.addAttribute("ingredientes",
                ingredienteRepo.findAll());
    }

    /* =======================  NUEVA  =========================== */
    @GetMapping("/nueva")
    public String nueva(Model model){
        if(!model.containsAttribute("recetaForm")){
            model.addAttribute("recetaForm", new RecetaForm());
        }
        cargarListaIngredientes(model);
        return "recetas/recetaEditar";
    }

    /* =======================  EDITAR  =========================== */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model){

        /* 1) Traemos la receta con todos sus items directamente del repo */
        var receta = recetaRepo.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        /* 2) Mapeamos la entidad -> RecetaForm (solo lo necesario para la pantalla) */
        var form = new RecetaForm();
        form.setId(receta.getId());
        form.setNombre(receta.getNombre());
        form.setDescripcion(receta.getDescripcion());

        for(ItemReceta ir : receta.getItems()){        // relación “items”
            var f = new ItemRecetaForm();
            f.setId(ir.getId());
            f.setIdIngrediente( ir.getIngrediente().getId() );
            f.setCantidadKg(ir.getCantidadKg().doubleValue());
            f.setCalorias(ir.getCalorias());
            form.getIngredientes().add(f);
        }

        /* 3) Al modelo */
        model.addAttribute("recetaForm", form);
        cargarListaIngredientes(model);

        return "recetas/recetaEditar";
    }

    /* =======================  GUARDAR  ========================== */
    @PostMapping
    public String guardar(@Valid @ModelAttribute("recetaForm") RecetaForm form,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model){

        if(br.hasErrors()){
            cargarListaIngredientes(model);   // necesitamos el combo si hay error
            return "recetas/recetaEditar";
        }

        if(form.getId()==null) recetaService.alta(form);
        else                   recetaService.editar(form.getId(), form);

        ra.addFlashAttribute("msg","Receta guardada correctamente");
        return "redirect:/recetas";
    }

    /* =======================  BAJA ============================== */
    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra){
        recetaService.baja(id);
        ra.addFlashAttribute("msg","Receta eliminada correctamente");
        return "redirect:/recetas";
    }
}
