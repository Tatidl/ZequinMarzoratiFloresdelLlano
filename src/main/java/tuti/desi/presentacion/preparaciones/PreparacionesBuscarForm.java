package tuti.desi.presentacion.preparaciones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PreparacionesBuscarForm {
    private String nombreReceta;
    private LocalDate fecha;
}
