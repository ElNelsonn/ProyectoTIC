package uy.edu.um.wtf.services;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private Validator validator;

    public Client addClient(Long id, String name, String surname, LocalDate birthDate, Long cardNumber) throws EntityAlreadyExistsException, InvalidDataException {

        // Control de duplicados
        if (clientRepo.findClientByCardNumber(cardNumber).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con ese n° de tarjeta.");
        }

        if (clientRepo.findClientByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un cliente con esa dni.");
        }

        // Crear un nuevo Client
        Client newClient = Client.builder().
                identityCard(id).
                name(name).
                surname(surname).
                birthDate(birthDate).
                cardNumber(cardNumber).
                build();

        // Validaciones
        ValidationUtil.validate(newClient, validator);

        // Agregar client
        return clientRepo.save(newClient);
    }


    public List<Client> allClient(){return clientRepo.findAll();}
}





