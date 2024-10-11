package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.*;
import java.time.LocalDateTime;
import java.util.Optional;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;
import uy.edu.um.wtf.utils.ValidationUtil;


import java.util.List;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketPurchaseRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;

    @Autowired
    private Validator validator;

    public TicketPurchase addTicketPurchase(Long clientId, List<Integer> seats, LocalDateTime purchaseDateTime, String cinemaName, String screenName, LocalDateTime movieScreeningDate) throws InvalidDataException, EntityNotFoundException {

        // Verificar existencia de entidades
        Optional<Client> clientOptional = clientRepo.findClientByIdentityCard(clientId);
        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un cliente con ese id.");
        }
        Client client = clientOptional.get();

        Optional<Cinema> cinemaOptional = cinemaRepo.findCinemaByName(cinemaName);
        if (cinemaOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un cine con ese nombre.");
        }
        Cinema cinema = cinemaOptional.get();

        Optional<Screen> screenOptional = screenRepo.findScreenByNameAndCinema(screenName, cinema);
        if (screenOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró un cine con ese nombre.");
        }
        Screen screen = screenOptional.get();

        Optional<MovieScreening> movieScreeningOptional = movieScreeningRepo.findMovieScreeningByScreenAndDate(screen, movieScreeningDate);
        if (movieScreeningOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontro esa funcion");
        }
        MovieScreening movieScreening = movieScreeningOptional.get();



        // Crear nuevo TicketPurchase
        TicketPurchase newTicketPurchase = TicketPurchase.builder().
                client(client).
                movieScreening(movieScreening).
                purchaseDate(purchaseDateTime).
                seats(seats).
                totalPrice((long) (seats.size() * 200)).
                build();

        // Validaciones
        ValidationUtil.validate(newTicketPurchase, validator);

        // Save new ticketPurchase
        return ticketPurchaseRepo.save(newTicketPurchase);

    }

    public List<TicketPurchase> allTicketPurchases(){return ticketPurchaseRepo.findAll();}



}