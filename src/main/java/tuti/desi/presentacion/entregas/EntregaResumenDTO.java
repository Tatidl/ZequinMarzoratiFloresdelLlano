package tuti.desi.presentacion.entregas;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EntregaResumenDTO {
    private Long id;
    private Integer nroFamilia;
    private String nombre;
    private String plato;
    private Integer cantidadRaciones;
    private LocalDate fecha;
}
