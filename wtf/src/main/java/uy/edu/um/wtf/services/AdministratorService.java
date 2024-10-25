package uy.edu.um.wtf.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.AdministratorRepository;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Administrator addAdministrator(Long id, String name, String surname, LocalDate birthDate, String email, String password) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de duplicados
        if (userRepo.findUserByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un admin con esa dni.");
        }

        // Crear un nuevo Admin
        Administrator newAdmin = Administrator.builder().
                identityCard(id).
                name(name).
                surname(surname).
                birthDate(birthDate).
                email(email).
                password(passwordEncoder.encode(password)).
                build();

        // Validaciones
        ValidationUtil.validate(newAdmin, validator);

        // Agregar nuevo Admin
        return adminRepo.save(newAdmin);
    }


    public Optional<Administrator> findAdministratorByEmail(String email) {

        return adminRepo.findAdministratorByEmail(email);
    }


    public List<Administrator> allAdministrators() {
        return adminRepo.findAll();
    }


}
