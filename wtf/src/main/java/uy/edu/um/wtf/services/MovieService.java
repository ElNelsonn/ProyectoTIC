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
        // Control de datos
        if (title == null || title.isEmpty()) {
            throw new InvalidDataException("El titulo no puede ser vacío.");
        }

        if (releaseDate == null || releaseDate.isAfter(LocalDate.now())) {
            throw new InvalidDataException("La fecha de lanzamiento no es valida.");
        }

        if (directors == null || directors.isEmpty()) {
            throw new InvalidDataException("El nombre del director no puede estar vacío.");
        }

        if (categories == null || categories.isEmpty()) {
            throw new InvalidDataException("Las categorías no pueden estar vacía.");
        }

        if (duration < 0) {
            throw new InvalidDataException("Duración de pelicula invalida.");
        }

        if (classification == null || classification.isEmpty()) {
            throw new InvalidDataException("La clasificación no puede estar vacía");
        }

        // Control de duplicados
        Optional<Movie> movieOptional = movieRepo.findMovieByTitle(title);
        if (movieOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe una pelicula con ese nombre.");
        }

        // Crear nueva Movie
        Movie newMovie = Movie.builder().
                title(title).
                releaseDate(releaseDate).
                directors(directors).
                synopsis(synopsis).
                actors(actors).
                duration(duration).
                classification(classification).
                categories(categories).
                build();

        // Guardar nueva Movie
        return movieRepo.save(newMovie);
    }

    public List<Movie> allMovies(){return movieRepo.findAll();}

}
