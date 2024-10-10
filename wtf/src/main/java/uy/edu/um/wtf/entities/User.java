package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @NotNull
    private Long id;

    @Column(name = "IDENTITY_CARD", unique = true, nullable = false)
    private Long identityCard;

    @Column(name = "NAME", nullable = false)
    @NotNull (message = "El nombre del cliente no puedo estar vacío.")
    @NotBlank (message = "El nombre del cliente no puedo estar vacío.")
    private String name;

    @Column(name = "SURNAME", nullable = false)
    @NotNull (message = "El apellido no puede estar vacío.")
    @NotBlank (message = "El apellido no puede estar vacío.")
    private String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE", nullable = false)
    @NotNull (message = "La fecha de nacimiento no puede estar vacía")
    @Past (message = "La fecha no esta dentro de un rango valido")
    private LocalDate birthDate;

}
