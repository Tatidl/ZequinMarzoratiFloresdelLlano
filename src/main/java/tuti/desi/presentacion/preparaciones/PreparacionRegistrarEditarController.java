package tuti.desi.presentacion.preparaciones;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.accesoDatos.IRecetaRepo;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.PreparacionService;

@Controller
@RequestMapping("/preparaciones")
@RequiredArgsConstructor
public class PreparacionRegistrarEditarController {

    private final PreparacionService preparacionService;
    private final IRecetaRepo recetaRepo;

    /* ---------- datos comunes a NUEVA y EDITAR ---------- */
    @ModelAttribute("recetas")
    public Iterable<?> recetas() {
        return recetaRepo.findByActivaTrueOrderByNombre();   // trae solo las activas
    }

    /* ---------- ALTA ---------- */
    @GetMapping("/nueva")
    public String nueva(Model model) {
        if (!model.containsAttribute("preparacionForm"))
            model.addAttribute("preparacionForm", new PreparacionForm());
        return "preparaciones/preparacionEditar";
    }

    /* ---------- EDICIÓN ---------- */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("preparacionForm", preparacionService.obtener(id));
        return "preparaciones/preparacionEditar";
    }

    /* ---------- GUARDAR (alta o edición) ---------- */
    @PostMapping
    public String guardar(@Valid @ModelAttribute("preparacionForm") PreparacionForm form,
                          BindingResult br,
                          RedirectAttributes ra) {
        System.out.println("Fecha enviada desde el formulario: " + form.getFecha());
        if (br.hasErrors()) {
            System.out.println("Errores de validación: " + br.getAllErrors());
            return "preparaciones/preparacionEditar";
        }

        try {
            if (form.getId() == null) {
                preparacionService.alta(form);
            } else {
                preparacionService.editarFecha(form.getId(), form);
            }
            ra.addFlashAttribute("msg", "Preparación guardada correctamente");
        } catch (Excepcion e) {
            System.out.println("Excepción en guardar: " + e.getMessage());
            ra.addFlashAttribute("error", e.getMessage());
            return "preparaciones/preparacionEditar";
        }

        return "redirect:/preparaciones";
    }

    /* ---------- BAJA LÓGICA ---------- */
    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra) {
        preparacionService.baja(id);
        ra.addFlashAttribute("msg", "Preparación eliminada correctamente");
        return "redirect:/preparaciones";
    }
}
