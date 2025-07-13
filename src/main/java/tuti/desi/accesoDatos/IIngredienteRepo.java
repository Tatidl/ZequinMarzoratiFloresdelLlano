package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.entidades.Ingrediente;

@Repository
public interface IIngredienteRepo extends JpaRepository<Ingrediente, Long> {
}
