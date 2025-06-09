/**
 * @author kuttel
 *
 */
package tuti.desi.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.accesoDatos.IProvinciaRepo;
import tuti.desi.entidades.Provincia;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.provincias.ProvinciasBuscarForm;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {
//	Logger LOG = LoggerFactory.getLogger(CiudadService.class);
//	
	@Autowired
	IProvinciaRepo repo;

	@Override
	public List<Provincia> getAll() {
		return repo.findAll();
	}



	@Override
	public Provincia getById(Long idProvincia) {
		return repo.findById(idProvincia).get();
	}
	
	@Override
	public void deleteByid(Long id) {
		repo.deleteById(id);
		
	}
	
	@Override
	public List<Provincia> filter(ProvinciasBuscarForm filter) throws Excepcion
	{
		//ver https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
		if(filter.getNombre()==null)
			return repo.findAll();
		else
			return repo.findByNombre(filter.getNombre());
				
	}
	
	@Override
	public void save(Provincia p) throws Excepcion {
		if(p.getId()==null)
		{ 
			//si llegó aquí es porque estoy registrando una nueva provincia 
			if(!repo.findByNombre(p.getNombre()).isEmpty()) 
				throw new Excepcion("Ya existe una provincia con el mismo nombre");  
			else
				repo.save(p);
				
		}
		else
		{
			//si llegó aquí es porque estoy editando una provincia existente
			if(!repo.findByNombreAndIdNot(p.getNombre(),p.getId()).isEmpty())  //tengo que validar que no exista otra provincia (in id diferente), con el mismo nombre
				throw new Excepcion("Existe otra provincia con el mismo nombre");
			else
				repo.save(p);
		}
		
	}

}
