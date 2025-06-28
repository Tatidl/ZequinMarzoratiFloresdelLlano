package tuti.desi.presentacion.entregas;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tuti.desi.accesoDatos.IPreparacionRepo;
import tuti.desi.entidades.EntregaAsistencia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.EntregaAsistenciaService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregasController {

    private final EntregaAsistenciaService entregaAsistenciaService;
    private final IPreparacionRepo preparacionRepo;

    // Listar
    @GetMapping
    public String listar(@RequestParam(required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                         LocalDate fecha,
                         @RequestParam(required = false) Long nroFamilia,
                         @RequestParam(required = false) String nombreFamilia,
                         Model model) {

        List<EntregaAsistencia> entregas = entregaAsistenciaService.listar(fecha, nroFamilia, nombreFamilia);
        model.addAttribute("entregas", entregas);
        return "entregas/listar";
    }

    // Nueva entrega
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("form", new EntregaForm());
        model.addAttribute("platos", preparacionRepo.findAll());
        return "entregas/crearEditar";
    }

    @PostMapping
    public String guardar(@ModelAttribute("form") @Valid EntregaForm form,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("platos", preparacionRepo.findAll());
            return "entregas/crearEditar";
        }
        try {
            Long idVoluntario = 1L; // Hacemos que el usuario sea siempre el mismo voluntario y listo.
            entregaAsistenciaService.alta(form, idVoluntario);
            return "redirect:/entregas";
        } catch (Excepcion e) {
            model.addAttribute("platos", preparacionRepo.findAll());
            model.addAttribute("error", e.getMessage());
            return "entregas/crearEditar";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) throws Excepcion {
        entregaAsistenciaService.baja(id);
        return "redirect:/entregas";
    }
}
