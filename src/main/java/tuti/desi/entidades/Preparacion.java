package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preparacion")
@Getter
@Setter
@NoArgsConstructor
public class Preparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id")
    private Receta receta;

    private Integer totalRacionesPreparadas;

    private Integer stockRacionesRestantes;

    private LocalDate fechaCoccion = LocalDate.now();

    @OneToMany(mappedBy = "preparacion", cascade = CascadeType.ALL)
    private List<EntregaAsistencia> entregas = new ArrayList<>();

    private boolean activa = true;
}