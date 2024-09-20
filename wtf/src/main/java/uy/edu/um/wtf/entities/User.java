package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long Id;

    @Column(name = "IDENTITY")
    private long Identity;

    @Column(name = "NAME")
    private String Name;

    @Column(name = "SURNAME")
    private String Surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date Birth_date;

}
