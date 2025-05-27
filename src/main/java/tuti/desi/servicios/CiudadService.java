package tuti.desi.servicios;


import java.util.List;

import tuti.desi.entidades.Ciudad;
import tuti.desi.excepciones.Excepcion;
import tuti.desi.presentacion.CiudadesBuscarForm;
/**
 * Clase que permite gestionar la entidad Ciudad en el sistema.
 * @author kuttel
 * @version 1.0
 */

public interface CiudadService {

	

	/**
	 * Obtiene la lista completa de ciudades
	 * @return Todas las ciudades
	 */
	List<Ciudad> getAll();
	
	/**
	 * Obtiene una ciudad determinada
	 * @param idCiudad Identificador de la ciudad buscada
	 * @return Ciudad encontrada
	 */
	Ciudad getById(Long idCiudad) ;
	
	List<Ciudad> filter(CiudadesBuscarForm filter) throws Excepcion;

	void deleteByid(Long id);

	void save(Ciudad c) throws Excepcion;

}
