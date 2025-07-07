package tuti.desi.presentacion.preparaciones;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PreparacionResumenDTO {
    private final Long id;
    private final LocalDate fecha;
    private final String nombreReceta;
    private final Integer raciones;
    private final Integer caloriasPlato;
}
