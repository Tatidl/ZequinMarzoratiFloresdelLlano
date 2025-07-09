package tuti.desi.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer dni;

    private String domicilio;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Ocupacion ocupacion;
}