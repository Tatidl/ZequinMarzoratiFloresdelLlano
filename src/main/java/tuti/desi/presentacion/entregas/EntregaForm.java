package tuti.desi.presentacion.entregas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EntregaForm {
    private Long id;

    @NotNull(message = "Debe indicar nro de familia")
    private Integer nroFamilia;

    @NotNull(message = "Debe seleccionar preparación (plato)")
    private Long preparacionId;

    @NotNull
    @Positive
    private Integer cantidadRaciones;

    private LocalDate fechaEntrega;
}
