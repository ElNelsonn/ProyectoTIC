package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.DTO.MovieForController;
import uy.edu.um.wtf.entities.*;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.MovieScreeningRepository;
import uy.edu.um.wtf.services.MovieService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private MovieScreeningRepository movieScreeningRepo;


    @GetMapping("/add")
    public String getAddMovie(Model model) {

        model.addAttribute("todayDate", LocalDate.now());
        return "movie-creation";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute @Valid MovieForController movie, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "movie-creation";
        }

        try {

            Movie newMovie = movieService.addMovie(
                    movie.getTitle(),
                    movie.getReleaseDate(),
                    Arrays.asList(movie.getDirectors().split(",")),
                    movie.getSynopsis(),
                    Arrays.asList(movie.getCategories().split(",")),
                    Arrays.asList(movie.getActors().split(",")),
                    movie.getDuration(),
                    movie.getClassification(),
                    movie.getPosterURL(),
                    movie.getImageURL()
            );

            redirectAttributes.addFlashAttribute("message", "Película agregada con exito.");
            return "redirect:/administrator/home";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "movie-creation";
        }

    }







    @GetMapping("/info")
    public String getMovieinfo(Model model, @RequestParam String title, @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario) {

        Optional<Movie> movieOp = movieRepo.findMovieByTitle(title);

        if (movieOp.isEmpty()) {

            model.addAttribute("errorMessage", "No se encontró la película con el título: " + title);
            return "redirect:/home";
        }

        boolean isClient = usuario.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"));

        System.out.println(isClient);


        Movie movie = movieOp.get();

        List<MovieScreening> allMovieScreeningForMovie = movieScreeningRepo.findMovieScreeningsByMovie(movie);

        List<String> infoMovieScreening = new LinkedList<>();

        for (MovieScreening movieScreening: allMovieScreeningForMovie) {

            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formatedMovieScreeningDate = movieScreening.getDate().format(formatter1);

            Screen screen = movieScreening.getScreen();
            Cinema cinema = screen.getCinema();

            infoMovieScreening.add(cinema.getName() + ", " + screen.getName() + ", " + formatedMovieScreeningDate);
            movieScreening.getSeats();
        }

        if (infoMovieScreening.isEmpty()) {
            infoMovieScreening = null;
        }

        String directors = String.join(", ", movie.getDirectors());
        String categories = String.join(", ", movie.getCategories());
        String actors = String.join(", ", movie.getActors());

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedReleaseDate = movie.getReleaseDate().format(formatter2);



        model.addAttribute("isClient", isClient);
        model.addAttribute("funciones", infoMovieScreening);
        model.addAttribute("actors", actors);
        model.addAttribute("directors", directors);
        model.addAttribute("categories", categories);
        model.addAttribute("movie", movie);
        model.addAttribute("dateFormated", formattedReleaseDate);
        model.addAttribute("signed", usuario != null);
        return "movie-ticket";
    }













}
