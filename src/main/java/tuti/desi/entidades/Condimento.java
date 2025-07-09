package tuti.desi.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "condimento")
@Getter
@Setter
@NoArgsConstructor
public class Condimento extends Ingrediente {
    // Sin campos adicionales por ahora
}