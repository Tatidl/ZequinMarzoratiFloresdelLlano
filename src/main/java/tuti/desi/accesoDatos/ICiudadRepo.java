package tuti.desi.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tuti.desi.entidades.Ciudad;

@Repository
public interface ICiudadRepo extends JpaRepository<Ciudad, Long> {

	//notar que aquí debí indicarle a JPA qué query espero que genere porque no le estoy pasando como parametro la "Provincia" sino su Id
	@Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre or c.provincia.id=:idProvinciaSeleccionada")
	List<Ciudad> findByNombreOrIdProvincia(String nombre, Long idProvinciaSeleccionada);
	
	@Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre and c.provincia.id=:idProvinciaSeleccionada")
	List<Ciudad> findByNombreAndIdProvincia(String nombre, Long idProvinciaSeleccionada);
	
	/**
	 * busca las ciudades que coincidan con el nombre indicado y no coincidan con el id indicado
	 * @param nombre
	 * @param idDistintoDe
	 * @return
	 */
	@Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre and c.provincia.id=:idProvinciaSeleccionada and c.provincia.id<>:idDistintoDe")
	List<Ciudad> findByNombreAndIdProvinciaAndIdNot(String nombre, Long idProvinciaSeleccionada,Long idDistintoDe);
	

}
