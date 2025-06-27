package tuti.desi.entidades;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto extends Ingrediente {

    private Float stockDisponible;
    private Float precioActual;
}
