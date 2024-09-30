package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;

import java.util.Date;
import java.util.List;

@Service
public class TicketPurchaseService {

    private boolean availableSeats(MovieScreening movieScreening, int[] seats){
        boolean[] unavailableSeats = movieScreening.getSeats();

    }

    @Autowired
    private TicketPurchaseRepository ticketRepo;

    public TicketPurchase addTicketPurchase(int[] seats, MovieScreening movieScreening, Client client) throws InvalidDataException, EntityAlreadyExistsException {
        // Control de datos

        if (movieScreening == null) {
            throw new InvalidDataException("No se puede reservar una funcion no existente");
        }
        if (client == null) {
            throw new InvalidDataException("No se puede crear una funcion para un cliente que no existe");
            }

        if (){

        }

        // Crear una nueva compra de tickets
        TicketPurchase newTicket = TicketPurchase.builder()
                .seat(seats)
                .movieScreening(movieScreening)
                .client(client)
                .build();
        return ticketRepo.save(newTicket);
    }
}
