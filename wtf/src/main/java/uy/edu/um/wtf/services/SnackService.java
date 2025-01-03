package uy.edu.um.wtf.services;


import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.utils.ValidationUtil;
import java.util.List;

@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepo;

    @Autowired
    private Validator validator;

    public Snack addSnack(String name, Long price, String imageURL) throws InvalidDataException, EntityAlreadyExistsException {

        if (snackRepo.findSnackByName(name).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe ese snack.");
        }

        Snack newSnack = Snack.builder().
                name(name).
                price(price).
                imageURL(imageURL).
                build();

        ValidationUtil.validate(newSnack, validator);

        return snackRepo.save(newSnack);
    }


    public List<Snack> allSnacks(){return snackRepo.findAll();}

}
