package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private String name;

    @ManyToOne
    @JoinColumn(name = "CINEMA_ID", nullable = false)
    private Cinema cinema;

    @Column(name = "COLUMNS",nullable = false)
    private Integer columms;

    @Column(name = "ROWS", nullable = false)
    private Integer rows;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MovieScreening> movieScreenings = new LinkedList<>();

    /*ver como comprobar que sala esta libre para cada fecha*/


}
