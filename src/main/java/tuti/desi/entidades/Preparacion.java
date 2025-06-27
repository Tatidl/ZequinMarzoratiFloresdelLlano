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
public class Preparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalRacionesPreparadas;
    private Integer stockRacionesRestantes;
    private LocalDate fechaCoccion;

    /* * ---- 1 Receta */
    @ManyToOne(optional = false)
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    /* 1 ---- * EntregaAsistencia */
    @OneToMany(mappedBy = "preparacion")
    @Builder.Default
    private Set<EntregaAsistencia> entregas = new HashSet<>();
}
