package tuti.desi.presentacion.preparaciones;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tuti.desi.servicios.PreparacionService;

@Controller
@RequestMapping("/preparaciones")
@RequiredArgsConstructor
public class PreparacionesBuscarController {

    private final PreparacionService preparacionService;

    @GetMapping
    public String listado(@ModelAttribute("form") PreparacionesBuscarForm form,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        var pagina = preparacionService.listar(form.getNombreReceta(), form.getFecha(), PageRequest.of(page, 10));
        model.addAttribute("preparaciones", pagina);
        return "preparaciones/preparacionesBuscar";
    }
}