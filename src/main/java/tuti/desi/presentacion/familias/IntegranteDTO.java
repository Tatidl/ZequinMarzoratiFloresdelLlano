package tuti.desi.presentacion.familias;

import java.time.LocalDate;
import tuti.desi.entidades.Ocupacion;

public record IntegranteDTO(
        Long id,
        Integer dni,
        String apellido,
        String nombre,
        LocalDate fechaNacimiento,
        Ocupacion ocupacion) {}
