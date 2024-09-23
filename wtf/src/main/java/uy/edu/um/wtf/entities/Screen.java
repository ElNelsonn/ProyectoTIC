package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "NAME", unique = false, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "CINEMA_ID", nullable = false)
    private Cinema cinema;

    @Column(name = "COLUMNS", nullable = false)
    private Integer columms;

    @Column(name = "ROWS", nullable = false)
    private Integer rows;

}
