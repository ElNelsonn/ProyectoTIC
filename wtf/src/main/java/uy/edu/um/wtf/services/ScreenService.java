package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    @Autowired
    private Validator validator;

    public Screen addScreen(String name, String cinemaName, Integer columns, Integer rows) throws InvalidDataException, EntityNotFoundException, EntityAlreadyExistsException {

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
                columns(columns).
                build();

        // Validaciones
        ValidationUtil.validate(newScreen, validator);

        // Agregar screen
        return screenRepo.save(newScreen);
    }


    public List<Screen> allScreens(){
        return screenRepo.findAll();}

}
