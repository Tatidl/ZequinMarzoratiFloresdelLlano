package tuti.desi.presentacion.familias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import tuti.desi.entidades.Ocupacion;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class IntegranteForm {
    private Long id;

    @NotNull(message = "DNI obligatorio")
    private Integer dni;

    @NotBlank
    private String apellido;

    @NotBlank
    private String nombre;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Debe indicar fecha de nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull
    private Ocupacion ocupacion;
}
