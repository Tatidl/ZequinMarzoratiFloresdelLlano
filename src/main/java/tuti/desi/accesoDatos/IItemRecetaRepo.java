package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.ItemReceta;

public interface IItemRecetaRepo extends JpaRepository<ItemReceta, Long> {
}
