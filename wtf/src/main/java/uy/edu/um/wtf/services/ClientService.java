package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;

import java.util.Date;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    public Client addClient(Long id, String name, String surname, Date birthDate, Long cardNumber) throws InvalidDataException, EntityAlreadyExistsException {
        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre del cine no puedo estar vacío.");
        }

        if (surname == null || surname.isEmpty()) {
            throw new InvalidDataException("La ubicación no puede estar vacía.");
        }

        if (birthDate == null) {
            throw new InvalidDataException("El mail no puede estar vacío.");
        }

        // Control de duplicados
        if (clientRepo.findClientByCardNumber(cardNumber).isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        if (clientRepo.findClientByIdentityCard(id).isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        // Crear un nuevo Client
        Client newClient = Client.builder().
                identityCard(id).
                name(name).
                surname(surname).
                birthDate(birthDate).
                cardNumber(cardNumber).
                build();

        // Agregar client
        return clientRepo.save(newClient);

    }





}
