package tuti.desi.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voluntario extends Persona {

    private Long nroSeguro;

    /* 1 ---- * EntregaAsistencia */
    @OneToMany(mappedBy = "voluntario")
    @Builder.Default
    private Set<EntregaAsistencia> entregasRealizadas = new HashSet<>();
}