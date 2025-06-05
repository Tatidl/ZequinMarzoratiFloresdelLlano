package tuti.desi.presentacion.provincias;

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
import tuti.desi.entidades.Ciudad;
import tuti.desi.entidades.Provincia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.CiudadService;
import tuti.desi.servicios.ProvinciaService;


@Controller
@RequestMapping("/provinciasBuscar")
public class ProvinciasBuscarController {
	@Autowired
    private ProvinciaService servicioProvincia;
   
	
    @RequestMapping(method=RequestMethod.GET)
    public String preparaForm(Model modelo) {
    	ProvinciasBuscarForm form =  new ProvinciasBuscarForm();
       modelo.addAttribute("formBean",form);
       return "provinciasBuscar";
    }
     
    
   
    @RequestMapping( method=RequestMethod.POST)
    public String submit( @ModelAttribute("formBean") @Valid ProvinciasBuscarForm  formBean,BindingResult result, ModelMap modelo,@RequestParam String action) throws Excepcion {
    	
    	
    	if(action.equals("actionBuscar"))
    	{
    		
    		try {
    			List<Provincia> provincias = servicioProvincia.filter(formBean);
    			modelo.addAttribute("resultados",provincias);
			} catch (Exception e) {
				ObjectError error = new ObjectError("globalError", e.getMessage());
	            result.addError(error);
			}
    		
    		modelo.addAttribute("formBean",formBean);
        	return "provinciasBuscar";
    	}
    	else if(action.equals("actionCancelar"))
    	{
    		modelo.clear();
    		return "redirect:/";
    	}
    	else if(action.equals("actionRegistrar"))
    	{
    		modelo.clear();
    		return "redirect:/provinciaEditar";
    	}
    		
    	return "redirect:/";
    	
    	
    }

 
}
