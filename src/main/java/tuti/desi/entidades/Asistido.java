package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "asistido")
@Getter
@Setter
@NoArgsConstructor
public class Asistido extends Persona {

    private LocalDate fechaRegistro = LocalDate.now();

    // Relación 1..* -> 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    // Para borrado lógico
    private boolean activo = true;
}