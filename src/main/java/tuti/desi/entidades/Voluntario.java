package tuti.desi.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voluntario")
@Getter
@Setter
@NoArgsConstructor
public class Voluntario extends Persona {

    @Column(nullable = false, unique = true)
    private String nroSeguro;
}