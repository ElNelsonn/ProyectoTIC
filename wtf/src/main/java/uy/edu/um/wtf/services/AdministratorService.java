package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.AdministratorRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository adminRepo;

    public Administrator addAdministrator(Long id, String name, String surname, LocalDate birthDate) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre del admin no puedo estar vacío.");
        }

        if (surname == null || surname.isEmpty()) {
            throw new InvalidDataException("El apellido no puede estar vacío.");
        }

        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new InvalidDataException("La fecha de nacimiento no es valida.");
        }

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
        return adminRepo.save(newAdmin);
    }

    public List<Administrator> allAdministrators() {
        return adminRepo.findAll();
    }


}
