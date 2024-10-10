package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    public Movie addMovie(String title, LocalDate releaseDate, List<String> directors, String synopsis, List<String> categories, List<String> actors, Long duration, String classification) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de duplicados

        if (movieRepo.findMovieByTitle(title).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe una pelicula con ese nombre.");
        }

        // Crear nueva Movie
        Movie newMovie = Movie.builder().
                title(title).
                releaseDate(releaseDate).
                directors(directors).
                synopsis(synopsis).
                actors(actors).
                duration(duration). //Al dar esta informacion al cliente darla en horas y minutos
                classification(classification).
                categories(categories).
                build();

        // Guardar nueva Movie
        return movieRepo.save(newMovie);
    }

    public List<Movie> allMovies(){return movieRepo.findAll();}

}
