package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDENTITY_CARD", unique = true, nullable = false)
    private Long identityCard;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

}
