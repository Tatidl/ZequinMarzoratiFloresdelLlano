package tuti.desi.presentacion.productos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import tuti.desi.entidades.Producto;
import tuti.desi.servicios.ProductoService;

@Controller
@RequestMapping("/productosBuscar")
public class ProductosBuscarController {
	@Autowired
	private ProductoService service;

	@RequestMapping(method = RequestMethod.GET)
	public String preparaForm(Model modelo) {
		ProductosBuscarForm form = new ProductosBuscarForm();
		modelo.addAttribute("formBean", form);
		return "productosBuscar";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(@ModelAttribute("formBean") @Valid ProductosBuscarForm formBean, BindingResult result,
			ModelMap modelo, @RequestParam String action) {

		if (action.equals("Buscar")) {

			try {
				List<Producto> productos = service.filter(formBean);
				modelo.addAttribute("resultados", productos);
			} catch (Exception e) {
				ObjectError error = new ObjectError("globalError", e.getMessage());
				result.addError(error);
			}
			modelo.addAttribute("formBean", formBean);
			return "productosBuscar";
		}

		if (action.equals("Cancelar")) {
			modelo.clear();
			return "redirect:/";
		}

		if (action.equals("Registrar")) {
			modelo.clear();
			return "redirect:/productosEditar";
		}

		return "redirect:/";

	}

}
