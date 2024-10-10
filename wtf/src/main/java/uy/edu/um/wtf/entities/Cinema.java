package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull (message = "El nombre del cine no puedo estar vacío.")
    @NotBlank (message = "El nombre del cine no puedo estar vacío.")
    private String name;

    @ElementCollection
    @CollectionTable(name = "CINEMA_PHONE_NUMBERS", joinColumns = @JoinColumn(name = "CINEMA_ID"))
    @Column(name = "PHONE_NUMBER")
    @NotNull (message = "El cine debe tener un telefono de contacto.")
    private List<Long> phoneNumber;

    @Column(name = "LOCATION", nullable = false)
    @NotNull (message = "La ubicación no puede estar vacía.")
    @NotBlank (message = "La ubicación no puede estar vacía.")
    private String location;

    @Column(name = "MAIL", unique = true, nullable = false)
    @NotNull (message = "El mail no puede estar vacío.")
    private String mail;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Screen> screenList = new LinkedList<>();

}
