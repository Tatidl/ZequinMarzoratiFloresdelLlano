package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "entrega_asistencia")
@Getter
@Setter
@NoArgsConstructor
public class EntregaAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preparacion_id", nullable = false)
    private Preparacion preparacion;

    @Column(nullable = false)
    private Integer cantidadRaciones;

    private boolean activa = true;
}