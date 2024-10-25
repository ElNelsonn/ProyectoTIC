package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

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
    @NotBlank(message = "El nombre del snack no puedo estar vacío. (null)")
    @NotNull(message = "El nombre del snack no puedo estar vacío.")
    @Size(min = 2, max = 30, message = "El nombre del snack debe tener entre 2 y 30 caracteres")
    private String name;

    @Column(name = "PRICE", nullable = false)
    @NotNull(message = "Debe tener un precio asignado.")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private Long price;

    @Column(name = "IMAGE")
    @URL(message = "URL no valida.")
    private String imageURL;

}
