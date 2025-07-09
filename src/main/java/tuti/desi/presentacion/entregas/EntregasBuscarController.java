package tuti.desi.presentacion.entregas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tuti.desi.servicios.EntregaAsistenciaService;

@Controller
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregasBuscarController {

    private final EntregaAsistenciaService entregaAsistenciaService;

    @GetMapping
    public String listado(@ModelAttribute("form") EntregasBuscarForm form,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        var pagina = entregaAsistenciaService.listar(form, PageRequest.of(page, 10));
        model.addAttribute("entregas", pagina);
        model.addAttribute("fecha", form.getFecha() != null ? form.getFecha() : "");
        model.addAttribute("nroFamilia", form.getNroFamilia() != null ? form.getNroFamilia() : "");
        model.addAttribute("nombre", form.getNombre() != null ? form.getNombre() : "");
        return "entregas/entregasBuscar";
    }
}