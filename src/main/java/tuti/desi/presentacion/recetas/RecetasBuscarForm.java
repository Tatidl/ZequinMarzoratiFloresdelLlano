package tuti.desi.presentacion.recetas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetasBuscarForm {
    private String nombre;
    private Integer minCal;
    private Integer maxCal;
}
