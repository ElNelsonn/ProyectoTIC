package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.Screen;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieScreeningRepository extends JpaRepository<MovieScreening, Long> {

    public List<MovieScreening> findMovieScreeningsByDate(LocalDateTime date);

    public List<MovieScreening> findMovieScreeningsByMovie(Movie movieName);

    public List<MovieScreening> findMovieScreeningsByScreen(Screen screen);

}
