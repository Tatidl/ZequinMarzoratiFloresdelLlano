package tuti.desi.presentacion.preparaciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PreparacionForm {
    private Long id;

    @PastOrPresent(message = "La fecha no puede ser futura")
    @NotNull(message = "Debe ingresar la fecha de preparación")
    private LocalDate fecha;

    @NotNull(message = "Debe seleccionar una receta")
    private Long idReceta;

    @Positive(message = "Cantidad de raciones no puede ser negativa")
    private Integer raciones;
}
