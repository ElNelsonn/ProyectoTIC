package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ScreenRepository;

import java.util.List;

public class ScreenService {

    @Autowired
    private ScreenRepository screenRepo;

    public Screen addScreen(String name, Cinema cinema, Integer columns, Integer rows) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre de la Screen no puedo estar vacío.");
        }

        if (cinema == null) {
            throw new InvalidDataException("El cine no es valido.");
        }

        if (columns < 0) {
            throw new InvalidDataException("Las columnas deben ser mayor a 0.");
        }

        if (rows < 0) {
            throw new InvalidDataException("Las filas deben ser mayor a 0.");
        }

        // Control de duplicados
        if (cinema.getScreenList().contains()) {
            throw new EntityAlreadyExistsException();
        }

        // Crear un nuevo Cinema
        Cinema newCinema = Cinema.builder()
                .name(name)
                .phoneNumber(phoneNumbers)
                .location(location)
                .mail(mail)
                .build();

        // Agregar cine
        return cinemaRepo.save(newCinema);
    }

    public boolean hasScreenWithName(Cinema cinema, String screenName) {
        

    }



}
