package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "SCREEN")

public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @NotNull(message = "El nombre no puede estar vacío.")
    @Size(min = 1, max = 8, message = "El nombre debe tener entre 1 y 8 caracteres")
    private String name;

    @ManyToOne
    @JoinColumn(name = "CINEMA_ID", nullable = false)
    @NotNull(message = "El cine no puede estar vacío.")
    private Cinema cinema;

    @Column(name = "COLUMNS",nullable = false)
    @NotNull(message = "Dimensiones no validas")
    @Positive(message = "Dimensiones no validas")
    private Integer columms;

    @Column(name = "ROWS", nullable = false)
    @NotNull(message = "Dimensiones no validas")
    @Positive(message = "Dimensiones no validas")
    private Integer rows;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MovieScreening> movieScreenings = new LinkedList<>();
}
