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
import tuti.desi.presentacion.ProvinciasBuscarForm;

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
		if(p.getId()==null && !repo.findByNombre(p.getNombre()).isEmpty()) //estoy dando de alta una nueva provincia y ya existe una igual?
			throw new Excepcion("Ya existe una provincia con el mismo nombre");  
		else
		{
			if(!repo.findByNombreAndIdNot(p.getNombre(),p.getId()).isEmpty()) //si edito el nombre, valido que no exista otra con el mismo nombre?
				throw new Excepcion("Existe otra provincia con el mismo nombre");
			else
				repo.save(p);
		}
		
	}

}
