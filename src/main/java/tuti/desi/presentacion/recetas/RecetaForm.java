package tuti.desi.presentacion.recetas;

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
public class RecetaForm {
    private Long id;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @Size(max = 4000)
    private String descripcion;

    // Lista de ingredientes
    @Valid
    @Size(min = 1, message = "Debe cargar al menos un ingrediente")
    private List<ItemRecetaForm> ingredientes = new ArrayList<>();


}
