package tuti.desi.presentacion.productos;

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
import tuti.desi.entidades.Producto;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.servicios.ProductoService;


@Controller
@RequestMapping("/productosEditar")
public class ProductosEditarController {
	@Autowired
    private ProductoService service;
	 
    @RequestMapping(path = {"", "/{id}"},method=RequestMethod.GET)
    public String preparaForm(Model modelo, @PathVariable("id") Optional<Long> dni) throws Exception {
    	if (dni.isPresent()) {
    		Producto entity = service.getProductoById(dni.get());
    		ProductoForm form = new ProductoForm(entity);
			modelo.addAttribute("formBean", form);
		} else {
 
	       modelo.addAttribute("formBean",new ProductoForm());
		}
       return "productosEditar";
    }
     
    
	
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
	public String deleteProductoById(Model model, @PathVariable("id") Long id) 
	{
		service.deleteProductoByid(id);
		return "redirect:/productosBuscar";
	}
 
    
    @RequestMapping( method=RequestMethod.POST)
    public String submit(@ModelAttribute("formBean") @Valid ProductoForm formBean,BindingResult result, ModelMap modelo,@RequestParam String action) throws Exception  {
    	
    	
    	if(action.equals("Aceptar"))
    	{
           
    		if(result.hasErrors())
    		{
    			modelo.addAttribute("formBean",formBean);
    			 return "productosEditar";
    		}
    		else
    		{
    			Producto p=formBean.toPojo();
    			try {
					service.save(p);
					
					return "redirect:/productosBuscar";
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
	    			return "productosEditar";//Como existe un error me quedo en la misma pantalla
				}
    		}

    		
        	
        	
    	}
    
    	
    	if(action.equals("Cancelar"))
    	{
    		modelo.clear();
    		return "redirect:/productosBuscar";
    	}
    		
    	return "redirect:/";
    	
    	
    }


 
}
