package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "SNACK")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull (message = "El nombre del snack no puedo estar vacío.")
    @NotBlank (message = "El nombre del snack no puedo estar vacío.")
    private String name;

    @Column(name = "GLUTEN_FREE", nullable = false)
    @NotNull (message = "Debe indicarse si es gluten free.")
    private Boolean glutenFree;

    @Column(name = "PRICE", nullable = false)
    @NotNull (message = "Debe tener un precio asignado.")
    @Positive (message = "El precio debe ser mayor que 0.")
    private Long price;

}
