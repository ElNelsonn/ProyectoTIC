package uy.edu.um.wtf.converters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.repository.CinemaRepository;

import java.util.Optional;

@Component
public class CinemaConverter implements Converter<String, Cinema> {

    @Autowired
    private CinemaRepository cinemaRepo;

    public Cinema convert(String cinemaName){

        Optional<Cinema> cinemaOptional = cinemaRepo.findCinemaByName(cinemaName);
        if (cinemaOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un cine con ese nombre.");
        }

        return cinemaOptional.get();
    }


}



