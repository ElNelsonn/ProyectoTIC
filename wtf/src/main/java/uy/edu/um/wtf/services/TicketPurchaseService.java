package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketPurchaseRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;

    @Autowired
    private MovieScreeningService movieScreeningService;

    @Autowired
    private Validator validator;


    public TicketPurchase addTicketPurchase(String email, String[] seatsSelected, Long moviescreeningId) throws InvalidDataException, EntityNotFoundException {

        // Verificar existencia de entidades
        Optional<Client> clientOptional = clientRepo.findClientByEmail(email);
        if (clientOptional.isEmpty()) {

            throw new EntityNotFoundException("No se encontró un cliente con ese id.");
        }
        Client client = clientOptional.get();

        Optional<MovieScreening> movieScreeningOptional = movieScreeningRepo.findMovieScreeningById(moviescreeningId);
        if (movieScreeningOptional.isEmpty()) {

            throw new EntityNotFoundException("No se encontró esa función.");
        }
        MovieScreening movieScreening = movieScreeningOptional.get();

        List<Seat> seatsFuncion = movieScreening.getSeats();

        List<Integer> seatsComprados = new LinkedList<>();

        int col = movieScreening.getScreen().getColumns();

        for (String seat: seatsSelected) {

            String[] seatArray = seat.split(", ");
            int filaSeat = Integer.parseInt(seatArray[0]);
            int colSeat = Integer.parseInt(seatArray[1]);

            Integer number = ((filaSeat*col) - col) + colSeat;

            seatsComprados.add(number);
        }


        List<Seat> seatDef = new LinkedList<>();

        for (Seat seat: seatsFuncion) {

            for (Integer seatSelected: seatsComprados) {

                if (Objects.equals(seat.getSeatNumber(), seatSelected)) {

                    seat.setIsOccupied(true);
                    seatDef.add(seat);
                    break;
                }
            }
        }


        LocalDate fechaGratisHasta = LocalDate.of(2025, 5, 16);
        int price = 300;

        if (LocalDate.now().isBefore(fechaGratisHasta)) {
            price = 0;
        }


        // Crear nuevo TicketPurchase
        TicketPurchase newTicketPurchase = TicketPurchase.builder().
                client(client).
                movieScreening(movieScreening).
                purchaseDate(LocalDateTime.now()).
                seats(seatDef).
                totalPrice((long) (seatDef.size() * price)).
                build();

        // Validaciones
        ValidationUtil.validate(newTicketPurchase, validator);

        movieScreening.setSeats(seatsFuncion);
        movieScreeningRepo.save(movieScreening);

        // Save new ticketPurchase
        return ticketPurchaseRepo.save(newTicketPurchase);

    }

    public List<TicketPurchase> purchasesByClient(String email) throws EntityNotFoundException {


        Optional<Client> clientOptional = clientRepo.findClientByEmail(email);
        if (clientOptional.isEmpty()) {

            throw new EntityNotFoundException("No se encontró un cliente con ese id.");
        }
        Client client = clientOptional.get();

        return ticketPurchaseRepo.findTicketPurchasesByClient(client);
    }

    public void cancelTicket(TicketPurchase ticketPurchase) {

        movieScreeningService.liberarAsientos(ticketPurchase);

        ticketPurchaseRepo.delete(ticketPurchase);
    }









}