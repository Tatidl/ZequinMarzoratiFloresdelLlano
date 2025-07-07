package tuti.desi.presentacion.preparaciones;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.servicios.PreparacionService;

@Controller
@RequestMapping("/preparaciones")
@RequiredArgsConstructor
public class PreparacionRegistrarEditarController {

    private final PreparacionService preparacionService;

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("preparacionForm", new PreparacionForm());
        return "preparaciones/preparacionEditar";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("preparacionForm") PreparacionForm form,
                          BindingResult br,
                          RedirectAttributes ra) {
        if (br.hasErrors()) return "preparaciones/preparacionEditar";
        if (form.getId() == null) preparacionService.alta(form); else preparacionService.editarFecha(form.getId(), form);
        ra.addFlashAttribute("msg", "Preparación guardada correctamente");
        return "redirect:/preparaciones";
    }

    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra) {
        preparacionService.baja(id);
        ra.addFlashAttribute("msg", "Preparación eliminada correctamente");
        return "redirect:/preparaciones";
    }
}