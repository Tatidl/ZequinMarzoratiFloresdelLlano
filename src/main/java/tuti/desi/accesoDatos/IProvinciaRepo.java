package tuti.desi.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.entidades.Provincia;

@Repository
public interface IProvinciaRepo extends JpaRepository<Provincia, Long> {

	/**
	 * busca las ciudades que coincidan con el id indicado
	 * @param nombre
	 * @return
	 */
	List<Provincia> findByNombre(String nombre);
	/**
	 * busca las provincias que coincidan con el nombre indicado y no coincidan con el id indicado
	 * @param nombre
	 * @param idDistintoDe
	 * @return
	 */
	List<Provincia> findByNombreAndIdNot(String nombre,Long idDistintoDe);
	
}
