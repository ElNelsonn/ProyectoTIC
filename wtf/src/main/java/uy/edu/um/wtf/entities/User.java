package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDate;

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
    private Long id;

    @Column(name = "IDENTITY_CARD", unique = true, nullable = false)
    @Digits(integer = 8, fraction = 0, message = "Cedula invalida.")
    private Long identityCard;

    @Column(name = "NAME", nullable = false)
    @NotNull (message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String name;

    @Column(name = "SURNAME", nullable = false)
    @NotNull (message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 30, message = "El apellido debe tener entre 2 y 30 caracteres")
    private String surname;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @NotNull (message = "El mail no puede estar vacio.")
    @Email(message = "Email no valido.")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "La contrasena no puede estar vacia")
    @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres.")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE", nullable = false)
    @NotNull (message = "La fecha de nacimiento no puede estar vacía")
    @Past (message = "La fecha no esta dentro de un rango valido")
    private LocalDate birthDate;


}
