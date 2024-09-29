package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Movie;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    public List<Movie> findMoviesByTitle(String title);

    public List<Movie> findMoviesByDirectorsContaining(String director);

    public List<Movie> findMoviesByCategoriesContaining(String category);

    public List<Movie> findMoviesByActorsContaining(String actor);

    public List<Movie> findMoviesByClassification(String classification);



    // Poner todos los find de fechas




}
