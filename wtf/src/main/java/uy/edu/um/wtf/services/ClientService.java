package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    public Client addClient(Long id, String name, String surname, Date birthDate, Long cardNumber) throws InvalidDataException, EntityAlreadyExistsException {

        // Control de datos
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("El nombre del cliente no puedo estar vacío.");
        }

        if (surname == null || surname.isEmpty()) {
            throw new InvalidDataException("El apellido no puede estar vacío.");
        }

        if (birthDate == null || birthDate.after(new Date())) {
            throw new InvalidDataException("La fecha de nacimiento no es valida.");
        }

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

        // Agregar client
        return clientRepo.save(newClient);
    }





}
