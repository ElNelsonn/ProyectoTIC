package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
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
    private String name;

    @ElementCollection
    @CollectionTable(name = "CINEMA_PHONE_NUMBERS", joinColumns = @JoinColumn(name = "CINEMA_ID"))
    @Column(name = "PHONE_NUMBER")
    private List<Long> phoneNumber;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column(name = "MAIL", unique = true, nullable = false)
    private String mail;


    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Screen> screenList = new LinkedList<>();

}
