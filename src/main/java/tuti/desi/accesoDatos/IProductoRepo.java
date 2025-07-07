package tuti.desi.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entidades.Producto;

public interface IProductoRepo extends JpaRepository<Producto, Long> {
}
