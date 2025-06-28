package tuti.desi.presentacion.entregas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaForm {

    @NotNull
    @Positive
    private Long nroFamilia;

    @NotNull
    private Long preparacionId;

    @NotNull
    @Positive
    private Integer cantidadRaciones;
}
