package tuti.desi.entidades;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Condimento extends Ingrediente {} // No hay más datos en el diagrama para esta clase....
