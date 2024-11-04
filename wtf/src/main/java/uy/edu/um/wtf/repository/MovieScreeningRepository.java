package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.Screen;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieScreeningRepository extends JpaRepository<MovieScreening, Long> {

    public List<MovieScreening> findMovieScreeningsByDate(LocalDateTime date);

    public List<MovieScreening> findMovieScreeningsByMovie(Movie movieName);

    public List<MovieScreening> findMovieScreeningsByScreen(Screen screen);

    public List<MovieScreening> findMovieScreeningsByScreenAndDateBetween(Screen screen, LocalDateTime startingTime, LocalDateTime endTime);

    public Optional<MovieScreening> findMovieScreeningByScreenAndDate(Screen screen, LocalDateTime dateTime);

    public Optional<MovieScreening> findMovieScreeningById(Long Id);

}
