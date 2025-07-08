package tuti.desi.presentacion.familias;

import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.FamiliaService;

@Controller
@RequestMapping("/familias")
@RequiredArgsConstructor
public class FamiliaController {

    private final FamiliaService familiaService;

    // ---------- LISTADO -----------------------------------------------------
    @GetMapping
    public String listado(@ModelAttribute("filtro") FamiliasBuscarForm filtro,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {

        var paginaDTO = familiaService.listar(
                filtro.getFiltro(),
                PageRequest.of(page, 10));

        model.addAttribute("page", paginaDTO);
        return "familias/familiasBuscar";
    }

    // ---------- ALTA --------------------------------------------------------
    @GetMapping("/nueva")
    public String nueva(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new FamiliaForm());
        }
        return "familias/familiaEditar";
    }

    // ---------- EDICIÓN -----------------------------------------------------
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("form")) {
            // Traemos la entidad completa y la adaptamos a un form
            FamiliaForm form = familiaService.obtenerParaEdicion(id);
            model.addAttribute("form", form);
        }
        return "familias/familiaEditar";
    }

    // ---------- GUARDAR (alta o edición) ------------------------------------
    @PostMapping
    public String guardar(@Valid @ModelAttribute("form") FamiliaForm form,
                          BindingResult br,
                          RedirectAttributes ra,
                          ServletRequest request,
                          Model model) {
        if (br.hasErrors()) {
            return "familias/familiaEditar";
        }

        try {
            if (form.getId() == null) {
                familiaService.alta(form);
                ra.addFlashAttribute("msg", "Familia registrada");
            } else {
                familiaService.editar(form.getId(), form);
                ra.addFlashAttribute("msg", "Familia actualizada");
            }
            return "redirect:/familias";
        } catch (Excepcion e) {
            // Agregar el mensaje de error al modelo para mostrarlo en la vista
            model.addAttribute("error", e.getMessage());
            return "familias/familiaEditar";
        }
    }

    // ---------- BAJA LÓGICA -------------------------------------------------
    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra) {
        familiaService.baja(id);
        ra.addFlashAttribute("msg", "Familia dada de baja");
        return "redirect:/familias";
    }
}
