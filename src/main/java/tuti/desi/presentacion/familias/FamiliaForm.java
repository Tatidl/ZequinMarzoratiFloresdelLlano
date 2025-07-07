package tuti.desi.presentacion.familias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FamiliaForm {
    private Long id;     // nulo cuando es alta

    private Integer nroFamilia; // se completa en el servicio (autonumérico)

    @NotBlank(message = "El nombre de la familia es obligatorio")
    private String nombre;

    // ----- Integrantes -----
    @Size(min = 1, message = "Debe cargar al menos un integrante")
    @Valid
    private List<IntegranteForm> integrantes = new ArrayList<>();
}