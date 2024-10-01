package uy.edu.um.wtf.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.SnackRepository;

@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepo;

    public Snack addSnack(String name, Boolean glutenFree, Long price) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre del snack no puedo estar vacío.");
        }

        if (glutenFree == null) {
            throw new InvalidDataException("Debe indicarse si es gluten free.");
        }

        if (price == null || price < 0 ) {
            throw new InvalidDataException("Precio no valido.");
        }

        // Control de duplicados
        if (snackRepo.findSnackByName(name).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe ese snack.");
        }

        // Crear nuevo snack
        Snack newSnack = Snack.builder().
                name(name).
                glutenFree(glutenFree).
                price(price).
                build();


        return snackRepo.save(newSnack);
    }






}
