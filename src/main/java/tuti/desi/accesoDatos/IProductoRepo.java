package tuti.desi.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.entidades.Producto;

@Repository
public interface IProductoRepo extends JpaRepository<Producto, Long> {
	
	List<Producto> findByNombreContaining(String nombre);
	
	boolean existsByNombre(String nombre);

	boolean existsByNombreAndIdNot(String nombre, Long id);
}
