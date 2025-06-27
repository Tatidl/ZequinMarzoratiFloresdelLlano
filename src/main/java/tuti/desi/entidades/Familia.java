package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroFamilia;

    private String nombre;
    private LocalDate fechaRegistro;

    /* 1 ---- * Asistido */
    @OneToMany(mappedBy = "familia",
            cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Asistido> integrantes = new HashSet<>();
}