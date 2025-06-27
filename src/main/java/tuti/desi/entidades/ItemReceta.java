package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Integer calorias;

    /* * ---- 1 Receta */
    @ManyToOne(optional = false)
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    /* * ---- 1 Ingrediente (abstracto) */
    @ManyToOne(optional = false)
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;
}
