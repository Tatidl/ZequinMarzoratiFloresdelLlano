package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Ingrediente;

public interface IIngredienteRepo extends JpaRepository<Ingrediente, Long> {
}
