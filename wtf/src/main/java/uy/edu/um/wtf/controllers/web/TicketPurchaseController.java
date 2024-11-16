package uy.edu.um.wtf.controllers.web;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.DTO.SeatForPurchase;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.MovieScreeningRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import uy.edu.um.wtf.services.TicketPurchaseService;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/ticket")
public class TicketPurchaseController {


    @Autowired
    private CinemaRepository cinemaRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private TicketPurchaseService ticketPurchaseService;


    @PostMapping("/purchase")
    public String getTicketPurchase(@RequestParam String title, @RequestParam String function, Model model,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario,
                                    RedirectAttributes redirectAttributes) {

        if (clientRepo.findClientByEmail(usuario.getUsername()).get().getCardNumber() == null
                || clientRepo.findClientByEmail(usuario.getUsername()).get().getCardExpirationDate().isBefore(LocalDate.now())) {

            redirectAttributes.addAttribute("title", title);
            redirectAttributes.addAttribute("errorMessages", "Método de pago rechazado.");
            return "redirect:/movie/info";
        }

        String[] strings = function.split(", ");

        String cinemaName = strings[0];
        String screenName = strings[1];
        String movieScreeningDate = strings[2];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime fechaHora = LocalDateTime.parse(movieScreeningDate, formatter);

        Optional<Cinema> cinemaOp = cinemaRepo.findCinemaByName(cinemaName);
        if (cinemaOp.isEmpty()) {

            redirectAttributes.addFlashAttribute("message", "Uy ha surgido un error.");
            return "redirect:/home";
        }
        Cinema cinema = cinemaOp.get();

        Optional<Screen> screenOp = screenRepo.findScreenByNameAndCinema(screenName, cinema);
        if (screenOp.isEmpty()) {

            redirectAttributes.addFlashAttribute("message", "Uy ha surgido un error.");
            return "redirect:/home";
        }
        Screen screen = screenOp.get();

        Optional<MovieScreening> movieScreeningOp = movieScreeningRepo.findMovieScreeningByScreenAndDate(screen, fechaHora);
        if (movieScreeningOp.isEmpty()) {

            redirectAttributes.addFlashAttribute("message", "Uy ha surgido un error.");
            return "redirect:/home";
        }
        MovieScreening movieScreening = movieScreeningOp.get();

        List<SeatForPurchase> seatsForPurchase = new ArrayList<>();

        List<Seat> seatsFromMoviescreening = movieScreening.getSeats();
        seatsFromMoviescreening.sort(Comparator.comparingInt(Seat::getSeatNumber));


        Integer col = screen.getColumns();
        Integer rows = screen.getRows();

        for (Seat seat: seatsFromMoviescreening) {

            SeatForPurchase newSeatForPurchase = new SeatForPurchase();

            newSeatForPurchase.setIsOccupied(seat.getIsOccupied());

            Integer seatC = null;
            Integer seatR = null;

            if ((seat.getSeatNumber() % col) == 0) {

                seatC = col;
                seatR = seat.getSeatNumber()/col;

            } else {

                seatR = (seat.getSeatNumber()/col) + 1;
                seatC = seat.getSeatNumber()%col;

            }

            String seatRS = Integer.toString(seatR);
            String seatCS = Integer.toString(seatC);

            newSeatForPurchase.setSeatsRowCol(seatRS + ", " + seatCS);

            seatsForPurchase.add(newSeatForPurchase);
        }

        model.addAttribute("seats", seatsForPurchase);
        model.addAttribute("title", title);
        model.addAttribute("movieScreeningId", movieScreening.getId());
        model.addAttribute("price", 250);
        return "ticket-purchase";
    }

    @PostMapping("/confirm")
    public String ticketPurchase(@RequestParam String selectedSeats, @RequestParam Long movieScreeningId,
                                 @RequestParam String title, RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario) {

        String[] seatArray = selectedSeats.split(";");

        try {

            ticketPurchaseService.addTicketPurchase(usuario.getUsername(), seatArray, movieScreeningId);

            redirectAttributes.addFlashAttribute("message", "Compra de tickets con exito.");
            return "redirect:/home";

        } catch (InvalidDataException | EntityNotFoundException e) {

            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error al realizar la compra.");
            return "redirect:/home";
        }
    }
}
