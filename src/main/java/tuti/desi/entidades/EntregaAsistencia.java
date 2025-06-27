package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregaAsistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private Integer cantidadRaciones;

    /* * ---- 1 Preparacion */
    @ManyToOne(optional = false)
    @JoinColumn(name = "preparacion_id", nullable = false)
    private Preparacion preparacion;

    /* * ---- 1 Asistido */
    @ManyToOne(optional = false)
    @JoinColumn(name = "asistido_id", nullable = false)
    private Asistido asistido;

    /* * ---- 1 Voluntario */
    @ManyToOne(optional = false)
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;
}
