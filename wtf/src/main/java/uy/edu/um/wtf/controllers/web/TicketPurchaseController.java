package uy.edu.um.wtf.controllers.web;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.MovieScreeningRepository;
import uy.edu.um.wtf.repository.ScreenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

            // Poner los errores
            return "redirect:/home";
        }
        Cinema cinema = cinemaOp.get();

        Optional<Screen> screenOp = screenRepo.findScreenByNameAndCinema(screenName, cinema);
        if (screenOp.isEmpty()) {

            // Poner los errores
            return "redirect:/home";
        }
        Screen screen = screenOp.get();

        Optional<MovieScreening> movieScreeningOp = movieScreeningRepo.findMovieScreeningByScreenAndDate(screen, fechaHora);
        if (movieScreeningOp.isEmpty()) {

            // Poner los errores
            return "redirect:/home";
        }
        MovieScreening movieScreening = movieScreeningOp.get();

        List<String> seatsFilaCol = new ArrayList<>();


        Integer col = screen.getColumns();
        Integer rows = screen.getRows();
        for (Seat seat: movieScreening.getSeats()) {

            if (!seat.getIsOccupied()) {

                Integer seatR = (screen.getColumns()*screen.getColumns())/seat.getSeatNumber();
                String seatRS = Integer.toString(seatR);

                Integer seatC = (screen.getColumns()*screen.getColumns())%seat.getSeatNumber();
                String seatCS = Integer.toString(seatC);

                seatsFilaCol.add(seatRS + ", " + seatCS);
            }
        }

        model.addAttribute("seats", seatsFilaCol);
        model.addAttribute("title", title);
        model.addAttribute("movieScreeningId", movieScreening.getId());
        model.addAttribute("price", 250);
        return "ticket-purchase";
    }

    @PostMapping("/confirm")
    public String ticketPurchase(@RequestParam String seat, @RequestParam String movieScreening, @RequestParam String moiveTitle) {


        return "sdf";
    }






}
