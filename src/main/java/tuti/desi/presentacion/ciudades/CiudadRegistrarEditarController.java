package tuti.desi.presentacion.ciudades;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import tuti.desi.entidades.Ciudad;
import tuti.desi.entidades.Provincia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.CiudadService;
import tuti.desi.servicios.ProvinciaService;


/**
 * este controller será usado tanto para dar de alta una nueva ciudad como para editar una ya creada
 * @author dardo
 *
 */
@Controller
@RequestMapping("/ciudadEditar")
public class CiudadRegistrarEditarController {
	@Autowired
    private CiudadService servicioCiudad;
	@Autowired
    private ProvinciaService servicioProvincia;
     
    @RequestMapping(path = {"", "/{id}"},method=RequestMethod.GET)
    public String preparaForm(Model modelo, @PathVariable("id") Optional<Long> id) throws Exception {
    	if (id.isPresent())  //Si viene con un id se trata de una edicion, sino se trata de un alta de una nueva ciudad
    	{  
    		//Estoy por editar una ciudad existente
    		Ciudad entity = servicioCiudad.getById(id.get());
    		CiudadForm form = new CiudadForm(entity);
			modelo.addAttribute("formBean", form);
		} else {
			//Estoy por crear una ciudad nueva
 	       modelo.addAttribute("formBean",new CiudadForm());
		}
       return "ciudadEditar";
    }
     
    @ModelAttribute("allProvincias")
    public List<Provincia> getAllProvincias() {
        return this.servicioProvincia.getAll();
    }
	
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
	public String deleteById(Model model, @PathVariable("id") Long id) 
	{
		servicioCiudad.deleteByid(id);
		return "redirect:/ciudadesBuscar";
	}
 
    
    @RequestMapping( method=RequestMethod.POST)
    public String submit(@ModelAttribute("formBean") @Valid  CiudadForm formBean,BindingResult result, ModelMap modelo,@RequestParam String action) {
    	
    	
    	if(action.equals("actionAceptar")) //presionó el botón aceptar
    	{
    		if(result.hasErrors())
    		{
    			modelo.addAttribute("formBean",formBean);
    			 return "ciudadEditar";
    		}
    		else
    		{
    			try {
					Ciudad p=formBean.toPojo();
					p.setProvincia(servicioProvincia.getById(formBean.getIdProvincia()));
					servicioCiudad.save(p);
					
					return "redirect:/ciudadesBuscar";
				} catch (Excepcion e) {
					if(e.getAtributo()==null) //si la excepcion estuviera referida a un atributo del objeto, entonces mostrarlo al lado del compornente (ej. dni)
					{
						ObjectError error = new ObjectError("globalError", e.getMessage());
			            result.addError(error);
					}
					else
					{
			    		FieldError error1 = new FieldError("formBean",e.getAtributo(),e.getMessage());
			            result.addError(error1);

					}
		            modelo.addAttribute("formBean",formBean);
	    			return "ciudadEditar";  //Como existe un error me quedo en la misma pantalla
				}
    		}
    	}
    	else if(action.equals("actionCancelar"))//presionó el botón cancelar
    	{
    		modelo.clear();
    		return "redirect:/ciudadesBuscar";
    	}
    		
    	return "redirect:/";
    	
    	
    }

 
}
