package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import java.util.Optional;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    public Screen addScreen(String name, String cinemaName, Integer columns, Integer rows) throws InvalidDataException, EntityNotFoundException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre no puede estar vacío.");
        }

        if (cinemaName == null || cinemaName.isEmpty()) {
            throw new InvalidDataException("El nombre del cine no puede estar vacío.");
        }

        if (columns == null || rows == null || columns < 0 || rows < 0) {
            throw new InvalidDataException("Dimensiones no validas");
        }

        // Verificar existencia de entidades
        Optional<Cinema> cinemaOptional = cinemaRepo.findCinemaByName(cinemaName);
        if (cinemaOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un cine con ese nombre.");
        }
        Cinema cinema = cinemaOptional.get();

        // Control de duplicados
        Optional<Screen> screenOptional = screenRepo.findScreenByNameAndCinema(name, cinema);
        if (screenOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya exsite una sala con ese nombre");
        }

        // Crear un nuevo MovieScreening
        Screen newScreen = Screen.builder().
                name(name).
                cinema(cinema).
                rows(rows).
                columms(columns).
                build();

        // Agregar screen
        return screenRepo.save(newScreen);
    }

    public Screen addScreen2(String name, Cinema cinema, Integer columns, Integer rows) throws InvalidDataException, EntityNotFoundException, EntityAlreadyExistsException {
        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre no puede estar vacío.");
        }

        if (cinema == null) {
            throw new InvalidDataException("El nombre del cine no puede estar vacío.");
        }

        if (columns == null || rows == null || columns < 0 || rows < 0) {
            throw new InvalidDataException("Dimensiones no validas");
        }

        // Verificar existencia de entidades
        if (cinemaRepo.findCinemaByName(cinema.getName()).isEmpty()) {
            throw new EntityNotFoundException("No se encontro ese cine.");
        }

        // Control de duplicados
        if (screenRepo.findScreenByNameAndCinema(name, cinema).isPresent()) {
            throw new EntityAlreadyExistsException("Ya exsite una sala con ese nombre");
        }

        // Crear un nuevo MovieScreening
        Screen newScreen = Screen.builder().
                name(name).
                cinema(cinema).
                rows(rows).
                columms(columns).
                build();

        // Agregar screen
        return screenRepo.save(newScreen);




    }

}
