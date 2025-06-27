package tuti.desi.entidades;
/* =====================  RECETAS  ===================== */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    /* 1 ---- * ItemReceta */
    @OneToMany(mappedBy = "receta",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<ItemReceta> items = new HashSet<>();

    /* 1 ---- * Preparacion */
    @OneToMany(mappedBy = "receta")
    @Builder.Default
    private Set<Preparacion> preparaciones = new HashSet<>();
}

/* ---------------------------------------------------- */