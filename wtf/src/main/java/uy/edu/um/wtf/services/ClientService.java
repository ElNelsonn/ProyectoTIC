package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.utils.ValidationUtil;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private Validator validator;

    public Client addClient(Long id, String name, String surname, LocalDate birthDate, String cardNumber, LocalDate cardExpiraton, String email, String password) throws EntityAlreadyExistsException, InvalidDataException {

        // Control de duplicados
        if (cardNumber != null && clientRepo.findClientByCardNumber(cardNumber).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con ese numero de tarjeta.");
        }

        if (clientRepo.findClientByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con esa dni.");
        }

        if (clientRepo.findClientByEmail(email).isPresent()) {
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
                password(password).
                build();

        // Validaciones
        ValidationUtil.validate(newClient, validator);

        // Agregar client
        return clientRepo.save(newClient);
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





