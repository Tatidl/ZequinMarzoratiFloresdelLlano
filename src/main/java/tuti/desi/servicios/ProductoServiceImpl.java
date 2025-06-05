package tuti.desi.servicios;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.accesoDatos.IProductoRepo;
import tuti.desi.entidades.Producto;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.productos.ProductosBuscarForm;

@Service
public class ProductoServiceImpl implements ProductoService {

	
	@Autowired
	IProductoRepo repo;
	
	@Override
	public List<Producto> getAll() {
		
		return repo.findAll();
	}

	@Override
	public List<Producto> filter(ProductosBuscarForm filter) throws Excepcion {
		
		if(filter.getNombre()==null || filter.getNombre().isEmpty())
			return repo.findAll();
		else
			return repo.findByNombreContaining(filter.getNombre());
	}

	@Override
	public void save(Producto entity) throws Excepcion {
		GregorianCalendar gc =new GregorianCalendar();
		gc.set(Calendar.YEAR, 2000);
		gc.set(Calendar.DATE, 1);
		gc.set(Calendar.MONTH, 1);
		
		if(entity.getFechaVencimiento().before(gc.getTime()))
			throw new Excepcion("El producto se encuentra vencido");  //error global mostrado arriba
		else if(repo.existsByNombre(entity.getNombre()))
			throw new Excepcion("ya existe un producto con el mismo nombre", "nombre");  //error asociado al campo dni
		else
			repo.save(entity);
		
	}

	@Override
	public Producto getProductoById(Long id) throws Exception {

		Optional<Producto> p = repo.findById(id);
		
		if(p!=null) {
			return p.get();
		} else {
			throw new Exception("No existe el producto con el id="+id);
		}
	}

	@Override
	public void deleteProductoByid(Long id) {
		//acá podríamos validar que el producto no tega stock antes de eliminarlo
		repo.deleteById(id);
		
	}

	
}
