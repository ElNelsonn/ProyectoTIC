package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private Validator validator;

    public Movie addMovie(String title, LocalDate releaseDate, List<String> directors, String synopsis, List<String> categories, List<String> actors, Long duration, String classification, String posterURL, String imageURL) throws InvalidDataException, EntityAlreadyExistsException {

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
                duration(duration).
                classification(classification).
                categories(categories).
                posterURL(posterURL).
                imageURL(imageURL).
                build();

        // Validaciones
        ValidationUtil.validate(newMovie, validator);

        // Save new Movie
        return movieRepo.save(newMovie);
    }

    public List<Movie> allMovies(){return movieRepo.findAll();}

    public List<Movie> byCategories(String category) throws InvalidDataException {
        if (category == null){
            throw new InvalidDataException("A category must be written");
        }
        else {
            return movieRepo.findMovieByCategories(category);
        }
    }

    //ratings
    public List<Movie> allMoviesbyRatingGood(){return movieRepo.findAllByOrderByRatingDesc();}

    public List<Movie> allMoviesbyRatingBad(){return movieRepo.findAllByOrderByRatingAsc();}

    public List<Movie> dateRangeGoodMovies(LocalDate start,LocalDate finish){return movieRepo.findAllByReleaseDateBetweenOrderByValueDesc(start,finish);}

    public List<Movie> dateRangeBadMovies(LocalDate start,LocalDate finish){return movieRepo.findAllByReleaseDateBetweenOrderByValueAsc(start,finish);}

}

