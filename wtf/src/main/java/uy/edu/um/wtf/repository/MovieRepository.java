package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    public Optional<Movie> findMovieByTitle(String title);

    public List<Movie> findMoviesByDirectorsContaining(String director);

    public List<Movie> findMovieByCategories(String category);

    public List<Movie> findMoviesByActorsContaining(String actor);

    public List<Movie> findMoviesByClassification(String classification);


}
