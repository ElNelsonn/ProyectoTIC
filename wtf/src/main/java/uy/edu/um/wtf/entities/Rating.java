package uy.edu.um.wtf.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "RATING")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "IDENTITY_CARD_OF_USER", unique = true, nullable = false)
    @Digits(integer = 8, fraction = 0, message = "Cedula invalida.")
    private Long identityCard;
    //se debe comprobar que se de una resenia a cada peli por persona

    @Column(name = "RATE", nullable = false)
    @NotNull(message = "se debe dar un puntaje de entre 1 y 10")
    @Min(1)
    @Max(10)
    private Integer puntaje;

}
