package uy.edu.um.wtf.services;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.utils.ValidationUtil;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepo;

    @Autowired
    private Validator validator;

    public Cinema addCinema(String name, Long phoneNumber, String location, String email) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de duplicados
        if (cinemaRepo.findCinemaByName(name).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cine con ese nombre.");
        }

        if (cinemaRepo.findCinemaByEmail(email).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cine con ese mail.");
        }

        // Crear un nuevo Cinema
        Cinema newCinema = Cinema.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .location(location)
                .email(email)
                .build();

        // Validaciones
        ValidationUtil.validate(newCinema, validator);

        // Agregar cine
        return cinemaRepo.save(newCinema);
    }







}
