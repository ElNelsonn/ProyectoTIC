package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    public Optional<Movie> findByTitle(String Title);

    public Optional<Movie> findByDirectors(List<String> Directors);
    /*ni idea si puede definirlo :|*/
}
