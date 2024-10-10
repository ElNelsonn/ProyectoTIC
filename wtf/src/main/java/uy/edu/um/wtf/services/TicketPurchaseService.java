package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;
=======
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;
>>>>>>> 3204ffc421355408442c69af43e0907cb572d2f2

import java.util.List;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketRepo;

<<<<<<< HEAD
    @Autowired
    private ClientRepository clientRepo;
=======
    public List<TicketPurchase> allTicketPurchases(){return ticketRepo.findAll();}
>>>>>>> 3204ffc421355408442c69af43e0907cb572d2f2

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    public TicketPurchase addTicketPurchase(int[] seats, LocalDateTime screenTime, String movieName, Long clientCardNumber) throws InvalidDataException, EntityAlreadyExistsException {


        Optional<Client> client = clientRepo.findClientByCardNumber(clientCardNumber);


    }
}