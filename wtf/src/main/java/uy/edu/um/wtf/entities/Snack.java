package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
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
    private String name;

    @Column(name = "GLUTEN_FREE", nullable = false)
    private Boolean glutenFree;

    @Column(name = "PRICE", nullable = false)
    private Long price;

}
