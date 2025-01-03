package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.utils.ValidationUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client addClient(Long id, String name, String surname, LocalDate birthDate, String cardNumber, LocalDate cardExpiraton, String email, String password) throws EntityAlreadyExistsException, InvalidDataException {

        if (userRepo.findUserByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con esa dni.");
        }

        if (userRepo.findUserByEmail(email).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con ese email.");
        }

        // Crear un nuevo Client
        Client newClient = Client.builder().
                identityCard(id).
                name(name).
                surname(surname).
                birthDate(birthDate).
                cardNumber(cardNumber).
                cardExpirationDate(cardExpiraton).
                email(email).
                password(passwordEncoder.encode(password)).
                build();

        // Validaciones
        ValidationUtil.validate(newClient, validator);

        // Agregar client
        return clientRepo.save(newClient);
    }

    public Client editClient(String email, String cardNumber, LocalDate cardExpirationDate, String password) throws EntityNotFoundException, InvalidDataException {

        Optional<Client> probClient = clientRepo.findClientByEmail(email);

        if (probClient.isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado.");
        }

        Client client = probClient.get();

        client.setCardNumber(cardNumber);
        client.setCardExpirationDate(cardExpirationDate);

        if (password.length() >= 8) {

            client.setPassword(passwordEncoder.encode(password));
        }

        if (!password.equals("") && password.length() < 8) {
            throw new InvalidDataException("Contrasena invalida, debe tener al menos 8 caracteres.");
        }

        ValidationUtil.validate(client, validator);

        return clientRepo.save(client);
    }



    public Optional<Client> findClientByEmail(String email) {

        return clientRepo.findClientByEmail(email);
    }


    public List<Client> allClients(){return clientRepo.findAll();}

    public List<Client> byName(String name) throws InvalidDataException {
        if (name == null){
            throw new InvalidDataException("Wrtie a name please");
        }
        else {
            return clientRepo.findClientsByName(name);
        }
    }

    public List<Client> bySurname(String surname) throws InvalidDataException {
        if (surname == null) {
            throw new InvalidDataException("Write a surname please");
        }
        else {
            return clientRepo.findClientsBySurname(surname);
        }
    }
}





