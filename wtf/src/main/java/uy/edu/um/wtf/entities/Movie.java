package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "MOVIE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE")
    private Date releaseDate;

    @ElementCollection
    @CollectionTable(name = "MOVIE_DIRECTORS",joinColumns = @JoinColumn(name="MOVIE_ID"))
    @Column(name = "DIRECTORS") /*no se si funciona con el @Column*/
    private List<String> directors;

    @Column(name = "SYNOPSIS")
    private String synopsis;

    @ElementCollection
    @CollectionTable(name = "MOVIE_CATEGORIES",joinColumns=@JoinColumn(name="MOVIE_ID"))
    @Column(name = "CATEGORIES")
    private List<String> categories;


    @ElementCollection
    @CollectionTable(name = "MOVIE_ACTORS",joinColumns = @JoinColumn(name="MOVIE_ID"))
    @Column(name = "ACTORS")
    private List<String> actors;

    @Temporal(TemporalType.TIME)
    @Column(name = "DURATION")
    private Time duration;

    @Column(name = "CLASIFICATION")
    private String clasification;

}
