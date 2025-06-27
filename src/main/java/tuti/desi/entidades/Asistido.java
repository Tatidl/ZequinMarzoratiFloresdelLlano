package tuti.desi.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistido extends Persona {
    private LocalDate fechaRegistro;

    /* * ---- 1 Familia */
    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;

    /* 1 ---- * EntregaAsistencia */
    @OneToMany(mappedBy = "asistido")
    @Builder.Default
    private Set<EntregaAsistencia> entregas = new HashSet<>();
}
