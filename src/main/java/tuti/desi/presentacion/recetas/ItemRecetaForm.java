package tuti.desi.presentacion.recetas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemRecetaForm {
    private Long id;

    @NotNull(message = "Debe seleccionar ingrediente")
    private Long idIngrediente; // Se elige de un combo

    @Positive(message = "La cantidad no puede ser negativa")
    private double cantidadKg;

    @Positive(message = "Calorías no puede ser negativo")
    private int calorias;
}
