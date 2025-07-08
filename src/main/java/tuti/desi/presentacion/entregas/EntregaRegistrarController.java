package tuti.desi.presentacion.entregas;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.accesoDatos.IPreparacionRepo;
import tuti.desi.servicios.EntregaAsistenciaService;

import java.time.LocalDate;

@Controller
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregaRegistrarController {

    private final EntregaAsistenciaService entregaAsistenciaService;
    private final IPreparacionRepo preparacionRepo;

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("entregaForm", new EntregaForm());
        model.addAttribute("preparacionesHoy", preparacionRepo.findByFechaCoccionAndActivaTrueAndStockRacionesRestantesGreaterThan(
                LocalDate.now(), 0));
        return "entregas/entregaEditar";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("entregaForm") EntregaForm form,
                          BindingResult br,
                          RedirectAttributes ra) {
        if (br.hasErrors()) {
            // Reagregar preparacionesHoy en caso de error para evitar que el select esté vacío
            ra.addFlashAttribute("entregaForm", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.entregaForm", br);
            ra.addFlashAttribute("preparacionesHoy", preparacionRepo.findByFechaCoccionAndActivaTrueAndStockRacionesRestantesGreaterThan(
                    LocalDate.now(), 0));
            return "redirect:/entregas/nueva";
        }
        entregaAsistenciaService.alta(form);
        ra.addFlashAttribute("message", "Entrega registrada correctamente");
        return "redirect:/entregas";
    }

    @PostMapping("/{id}/baja")
    public String baja(@PathVariable Long id, RedirectAttributes ra) {
        entregaAsistenciaService.baja(id);
        ra.addFlashAttribute("message", "Entrega eliminada correctamente");
        return "redirect:/entregas";
    }
}