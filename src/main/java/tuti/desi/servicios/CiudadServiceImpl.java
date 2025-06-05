/**
 * @author kuttel
 *
 */
package tuti.desi.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.accesoDatos.ICiudadRepo;
import tuti.desi.entidades.Ciudad;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.ciudades.CiudadesBuscarForm;

@Service
public class CiudadServiceImpl implements CiudadService {
//	Logger LOG = LoggerFactory.getLogger(CiudadService.class);
//	
	@Autowired
	ICiudadRepo repo;

	@Override
	public List<Ciudad> getAll() {
		
		return repo.findAll();
	}



	@Override
	@Deprecated
	public Ciudad getById(Long idCiudad) {

		return repo.findById(idCiudad).get();
	}
	
	@Override
	public List<Ciudad> filter(CiudadesBuscarForm filter) throws Excepcion
	{
		//ver https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
		if(filter.getNombre()==null && filter.getProvinciaSeleccionada()==null)
			//return repo.findAll();
			throw new Excepcion("Es necesario al menos un filtro");
		else
			return repo.findByNombreOrIdProvincia(filter.getNombre(),filter.getProvinciaSeleccionada());
				
	}



	@Override
	public void deleteByid(Long id) {
		repo.deleteById(id);
		
	}



	@Override
	public void save(Ciudad c) throws Excepcion {
		if(c.getId()==null && !repo.findByNombreAndIdProvincia(c.getNombre(), c.getProvincia().getId()).isEmpty()) //estoy dando de alta una nueva ciudad y ya existe una igual?
			throw new Excepcion("Ya existe una ciudad con el mismo nombre, para la misma provincia");  
		else
		{
			if(!repo.findByNombreAndIdProvinciaAndIdNot(c.getNombre(), c.getProvincia().getId(),c.getId()).isEmpty()) //si edito el nombre, valido que no exista otra con el mismo nombre?
				throw new Excepcion("Existe otra ciudad con el mismo nombre para la misma provincia");
			else
				repo.save(c);
		}
		
	}

}
