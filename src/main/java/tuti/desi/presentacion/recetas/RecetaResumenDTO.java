package tuti.desi.presentacion.recetas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecetaResumenDTO {
    private Long id;
    private String nombre;
    private int caloriasTotales;
}
