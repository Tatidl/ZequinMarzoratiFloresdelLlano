package tuti.desi.presentacion.entregas;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EntregasBuscarForm {
    private LocalDate fecha;
    private Integer nroFamilia;
    private String nombre;
}
