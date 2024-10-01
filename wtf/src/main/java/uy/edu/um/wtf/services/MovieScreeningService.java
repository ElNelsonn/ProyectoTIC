package uy.edu.um.wtf.services;

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
import java.time.LocalDateTime;
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

    public MovieScreening addMovieScreening(LocalDateTime date, String movieName, String screenName, String cinemaName) throws InvalidDataException, EntityAlreadyExistsException, EntityNotFoundException {

        // Control de datos
        if (date == null || date.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("La fecha ingresada no es valida.");
        }

        if (movieName == null || movieName.isEmpty()) {
            throw new InvalidDataException("La pelicula no es valida.");
        }

        if (screenName == null || screenName.isEmpty()) {
            throw new InvalidDataException("La Screen no es valida.");
        }

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
        if (ScreenInUse(screen, date)) {
            throw new EntityAlreadyExistsException("Sala en uso durante ese horario");
        }

        // Crear un nuevo MovieScreening
        MovieScreening newMovieScreening = MovieScreening.builder()
                .date(date)
                .movie(movie)
                .screen(screen)
                .seats(new boolean[screen.getColumms() * screen.getRows()])
                .build();

        // Agregar MovieScreening
        return movieScreeningRepo.save(newMovieScreening);
    }


    // ADD CON OBJETOS
    public MovieScreening addMovieScreening2(LocalDateTime date, Movie movie, Screen screen, Cinema cinema) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (date == null || date.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("La fecha ingresada no es valida.");
        }

        if (movie == null) {
            throw new InvalidDataException("La pelicula no es valida.");
        }

        if (screen == null) {
            throw new InvalidDataException("La Screen no es valida.");
        }

        if (cinema == null) {
            throw new InvalidDataException("La Screen no es valida.");
        }

        // Control de duplicados
        if (ScreenInUse(screen, date)) {
            throw new EntityAlreadyExistsException("Sala en uso durante ese horario");
        }

        // Crear un nuevo MovieScreening
        MovieScreening newMovieScreening = MovieScreening.builder()
                .date(date)
                .movie(movie)
                .screen(screen)
                .seats(new boolean[screen.getColumms() * screen.getRows()])
                .build();

        // Agregar MovieScreening
        return movieScreeningRepo.save(newMovieScreening);
    }

    // Función para verificar si ya hay una función en la sala y horarios dados
    private boolean ScreenInUse(Screen screen, LocalDateTime dateTime) {

        LocalDateTime startingTime = dateTime.minusHours(3);
        LocalDateTime endTime = dateTime.plusHours(3);

        List<MovieScreening> movieScreeningOptional = movieScreeningRepo.findMovieScreeningsByScreenAndDateBetween(screen, startingTime, endTime);

        return !movieScreeningOptional.isEmpty();
    }








}
