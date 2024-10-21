package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CINEMA")

public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    @NotNull(message = "El nombre del cine no puedo estar vacío.")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String name;

    @Column(name = "PHONE_NUMBER")
    @NotNull (message = "El cine debe tener un telefono de contacto.")
    private Long phoneNumber;

    @Column(name = "LOCATION", nullable = false)
    @NotBlank(message = "La ubicación no puede estar vacía.")
    @NotNull(message = "La ubicación no puede estar vacía.")
    private String location;

    @Column(name = "EMAIL", unique = true, nullable = false)
    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotBlank(message = "El email no puede estar vacío.")
    private String email;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Screen> screenList = new LinkedList<>();

}
