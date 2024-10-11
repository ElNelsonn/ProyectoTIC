package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.MovieScreeningRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import javax.swing.table.TableRowSorter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieScreeningService {

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private Validator validator;

    public MovieScreening addMovieScreening(LocalDateTime date, String movieName, String screenName, String cinemaName) throws InvalidDataException, EntityAlreadyExistsException, EntityNotFoundException {

        // Verificar existencia de entidades
        Optional<Movie> movieOptional = movieRepo.findMovieByTitle(movieName);
        if (movieOptional.isEmpty()) {
            throw new EntityNotFoundException("Pelicula no encontrada.");
        }
        Movie movie = movieOptional.get();

        Optional<Cinema> cinemaOptional = cinemaRepo.findCinemaByName(cinemaName);
        if (cinemaOptional.isEmpty()) {
            throw new EntityNotFoundException("Cine no encontrado.");
        }
        Cinema cinema = cinemaOptional.get();

        Optional<Screen> screenOptional = screenRepo.findScreenByNameAndCinema(screenName, cinema);
        if (screenOptional.isEmpty()) {
            throw new EntityNotFoundException("Sala no encontrada.");
        }
        Screen screen = screenOptional.get();

        // Control de duplicados
        if (ScreenInUse(screen, date, movie.getDuration())) {
            throw new EntityAlreadyExistsException("Sala en uso durante ese horario");
        }

        // Crear un nuevo MovieScreening
        MovieScreening newMovieScreening = MovieScreening.builder()
                .date(date)
                .movie(movie)
                .screen(screen)
                .seats(newSeatsList(screen.getRows(), screen.getColumms()))
                .build();

        // Validaciones
        ValidationUtil.validate(newMovieScreening, validator);

        // Agregar MovieScreening
        return movieScreeningRepo.save(newMovieScreening);
    }

    private List<Boolean> newSeatsList(Integer rows, Integer columns) {

        List<Boolean> newSeats = new LinkedList<>();

        for (int i = 1; i <= (rows*columns); i++) {
            newSeats.add(false);
        }

        return newSeats;
    }

    private boolean ScreenInUse(Screen screen, LocalDateTime dateTime, Long duration) {

        List<MovieScreening> movieScreeningOptional = movieScreeningRepo.findMovieScreeningsByScreenAndDateBetween(screen, dateTime.minusMinutes(20), dateTime.plusMinutes(duration + 20));
        if (!movieScreeningOptional.isEmpty()) {
            return true;
        }

        movieScreeningOptional = movieScreeningRepo.findMovieScreeningsByScreenAndDateBetween(screen, dateTime.minusHours(4), dateTime.minusMinutes(20));

        for (MovieScreening movieScreening : movieScreeningOptional) {

            if (movieScreening.getDate().plusMinutes(movieScreening.getMovie().getDuration()).isAfter(dateTime.minusMinutes(20))) {
                return true;
            }
        }

        return false;
    }

    public List<MovieScreening> allMovieScreenings(){
        return movieScreeningRepo.findAll();
    }




}
