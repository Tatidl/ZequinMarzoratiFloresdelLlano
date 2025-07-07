package tuti.desi.presentacion.recetas;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.servicios.RecetaService;

@Controller
@RequestMapping("/recetas")
@RequiredArgsConstructor
public class RecetasRegistrarEditarController {

    private final RecetaService recetaService;

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("recetaForm", new RecetaForm());
        return "recetas/recetaEditar";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var receta = recetaService.listar(null, null, null, PageRequest.of(0,1))
                .stream().filter(r -> r.getId().equals(id)).findFirst().orElseThrow();
        var recetaForm = new RecetaForm();
        recetaForm.setId(id);
        recetaForm.setNombre(receta.getNombre());
        model.addAttribute("recetaForm", recetaForm);
        return "recetas/recetaEditar";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("recetaForm") RecetaForm recetaForm,
                          BindingResult br,
                          RedirectAttributes ra) {
        if (br.hasErrors()) return "recetas/recetaEditar";
        if (recetaForm.getId() == null) recetaService.alta(recetaForm); else recetaService.editar(recetaForm.getId(), recetaForm);
        ra.addFlashAttribute("msg", "Receta guardada correctamente");
        return "redirect:/recetas";
    }

    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra) {
        recetaService.baja(id);
        ra.addFlashAttribute("msg", "Receta eliminada correctamente");
        return "redirect:/recetas";
    }
}