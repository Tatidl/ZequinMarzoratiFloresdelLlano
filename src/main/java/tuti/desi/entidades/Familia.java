package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "familia")
@Getter
@Setter
@NoArgsConstructor
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Número correlativo de familia (auto‑generado en servicio) */
    @Column(nullable = false, unique = true)
    private Integer nroFamilia;

    @Column(nullable = false)
    private String nombre; // Ej: "Ramona Pérez e hijos"

    private LocalDate fechaRegistro = LocalDate.now();

    private boolean activa = true; // borrado lógico

    // 1..* integrantes activos
    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistido> integrantes = new ArrayList<>();
}