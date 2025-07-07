package tuti.desi.presentacion.familias;

import java.time.LocalDate;
import java.util.List;

public record FamiliaResumenDTO(
        Long id,
        Integer nroFamilia,
        String nombre,
        Integer integrantesActivos,
        LocalDate fechaRegistro,
        List<IntegranteDTO> integrantes) {}
