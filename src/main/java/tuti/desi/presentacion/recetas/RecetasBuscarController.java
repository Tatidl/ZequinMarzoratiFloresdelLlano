package tuti.desi.presentacion.recetas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tuti.desi.servicios.RecetaService;

@Controller
@RequestMapping("/recetas")
@RequiredArgsConstructor
public class RecetasBuscarController {

    private final RecetaService recetaService;

    @GetMapping
    public String listado(@ModelAttribute("form") RecetasBuscarForm form,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        if (form.getMinCal() != null && form.getMaxCal() != null && form.getMinCal() > form.getMaxCal()) {
            throw new IllegalArgumentException("Las calorías mínimas no pueden ser mayores que las máximas.");
        }

        var pagina = recetaService.listar(form.getNombre(), form.getMinCal(), form.getMaxCal(), PageRequest.of(page, 10));
        model.addAttribute("pagina", pagina);
        model.addAttribute("nombre", form.getNombre() != null ? form.getNombre() : "");
        model.addAttribute("minCal", form.getMinCal() != null ? form.getMinCal() : "");
        model.addAttribute("maxCal", form.getMaxCal() != null ? form.getMaxCal() : "");
        return "recetas/recetasBuscar";
    }
}