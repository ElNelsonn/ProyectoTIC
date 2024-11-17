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
import uy.edu.um.wtf.repository.*;
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

    @Autowired
    private TicketPurchaseRepository ticketPurchaseRepo;


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

        LocalDate fechaGratisHasta = LocalDate.of(2025, 5, 16);
        int price = 300;

        if (LocalDate.now().isBefore(fechaGratisHasta)) {
            price = 0;
        }

        model.addAttribute("seats", seatsForPurchase);
        model.addAttribute("title", title);
        model.addAttribute("movieScreeningId", movieScreening.getId());
        model.addAttribute("price", price);
        model.addAttribute("maxColumns", col);
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


    @GetMapping("/mytickets")
    public String misTickets(@AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario, Model model,
                             RedirectAttributes redirectAttributes) {

        try {

            List<TicketPurchase> ticketPurchases = ticketPurchaseService.purchasesByClient(usuario.getUsername());


            if (ticketPurchases.isEmpty()) {

                redirectAttributes.addFlashAttribute("message", "No has comprado ningún ticket.");
                return "redirect:/client/profile";
            }

            List<List<String>> bloqueInfo = new LinkedList<>();

            for (TicketPurchase ticketPurchase: ticketPurchases) {

                List<String> info = new LinkedList<>();

                MovieScreening movieScreening = ticketPurchase.getMovieScreening();

                String idTicketPurchase = Long.toString(ticketPurchase.getId());

                String movieTitle = movieScreening.getMovie().getTitle();

                String cinemaName = movieScreening.getScreen().getCinema().getName();

                String screenName = movieScreening.getScreen().getName();

                LocalDateTime fechaDeFuncion = movieScreening.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String fechaString = fechaDeFuncion.format(formatter);


                Integer col = movieScreening.getScreen().getColumns();
                List<Seat> seats = ticketPurchase.getSeats();
                String seatsStrings = "Asientos: ";

                for (Seat seat: seats) {

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

                    seatsStrings = seatsStrings + "(" + seatRS + ", " + seatCS + ") ";

                }

                info.add(idTicketPurchase);
                info.add(movieTitle + " Cine: " + cinemaName + " Sala: " + screenName + " Fecha: " + fechaString);
                info.add(seatsStrings);

                bloqueInfo.add(info);
            }

            model.addAttribute("bloqueInfo", bloqueInfo);
            return "mytickets";


        } catch (EntityNotFoundException e) {

            return "redirect:/home";
        }
    }


    @PostMapping("/cancel")
    public String cancelTicket(@AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario,
                               Model model, RedirectAttributes redirectAttributes, @RequestParam Long ticketId) {

        Optional<TicketPurchase> ticketACancelarOp = ticketPurchaseRepo.findById(ticketId);
        if (ticketACancelarOp.isEmpty()) {

            return "redirect:/client/profile";
        }
        TicketPurchase ticketACancelar = ticketACancelarOp.get();

        if (LocalDateTime.now().plusDays(2).isAfter(ticketACancelar.getMovieScreening().getDate())) {

            redirectAttributes.addFlashAttribute("message", "No puedes cancelar, ya es muy tarde...");
            return "redirect:/client/profile";
        }

        ticketPurchaseService.cancelTicket(ticketACancelar);

        redirectAttributes.addFlashAttribute("message", "Ticket cancelado con exito.");
        return "redirect:/ticket/mytickets";
    }






















}
