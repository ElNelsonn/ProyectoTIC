package uy.edu.um.wtf.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;

import java.util.List;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepo;

    public Cinema addCinema(String name, List<Long> phoneNumbers, String location, String mail) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre del cine no puedo estar vacío.");
        }

        if (location == null || location.isEmpty()) {
            throw new InvalidDataException("La ubicación no puede estar vacía.");
        }

        if (mail == null || mail.isEmpty()) {
            throw new InvalidDataException("El mail no puede estar vacío.");
        }

        // Control de duplicados
        if (cinemaRepo.findCinemaByName(name).isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        if (cinemaRepo.findCinemaByMail(mail).isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        // Crear un nuevo objeto Cinema
        Cinema newCinema = Cinema.builder()
                .name(name)
                .phoneNumber(phoneNumbers)
                .location(location)
                .mail(mail)
                .build();

        // Agregar cine
        return cinemaRepo.save(newCinema);
    }







}
