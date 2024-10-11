package uy.edu.um.wtf.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.AdministratorRepository;
import uy.edu.um.wtf.utils.ValidationUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private Validator validator;

    public Administrator addAdministrator(Long id, String name, String surname, LocalDate birthDate) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de duplicados
        if (adminRepo.findAdministratorByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un admin con esa dni.");
        }

        // Crear un nuevo Admin
        Administrator newAdmin = Administrator.builder().
                identityCard(id).
                name(name).
                surname(surname).
                birthDate(birthDate).
                build();

        // Validaciones
        ValidationUtil.validate(newAdmin, validator);

        // Agregar nuevo Admin
        return adminRepo.save(newAdmin);
    }

    public List<Administrator> allAdministrators() {
        return adminRepo.findAll();
    }


}
