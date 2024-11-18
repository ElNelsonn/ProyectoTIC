package uy.edu.um.wtf.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import uy.edu.um.wtf.services.MovieScreeningService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/moviescreening")
public class MovieScreeningController {

    @Autowired
    private MovieScreeningService movieScreeningService;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @GetMapping("/create")
    public String getMovieScreeningCreate(Model model) {

        List<String> screenAndCinema = allScreenAndCinema();
        List<String> movieTitle = allMoviesNames();

        if (movieTitle.isEmpty() || screenAndCinema.isEmpty()) {


            return "redirect:/home";
        }

        model.addAttribute("todayDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        model.addAttribute("screensCinemas", screenAndCinema);
        model.addAttribute("moviesTitle", movieTitle);
        return "moviescreening-creation";
    }

    @PostMapping("/create")
    public String movieScreeningCreate(@RequestParam LocalDateTime date, @RequestParam String screenCinema,@RequestParam String title, Model model, RedirectAttributes redirectAttributes) {

        String[] partes = screenCinema.split(", ");
        String cinemaName = partes[0];
        String screenName = partes[1];

        try {

            MovieScreening newMovieScreening = movieScreeningService.addMovieScreening(
                    date,
                    title,
                    screenName,
                    cinemaName
            );

            redirectAttributes.addFlashAttribute("message", "Función creada con exito.");
            return "redirect:/administrator/home";

        } catch (EntityAlreadyExistsException | InvalidDataException | EntityNotFoundException e) {

            List<String> screenAndCinema = allScreenAndCinema();
            List<String> movieTitle = allMoviesNames();

            List<String> errorMessages = new ArrayList<>();

            errorMessages.add(e.getMessage());

            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("todayDate", LocalDateTime.now());
            model.addAttribute("screensCinemas", screenAndCinema);
            model.addAttribute("moviesTitle", movieTitle);

            return "moviescreening-creation";

        }
    }

    public List<String> allScreenAndCinema() {
        List<Screen> allScreens = screenRepo.findAll();
        List<String> screenAndCinema = new LinkedList<>();

        for (Screen screen: allScreens) {
            screenAndCinema.add(screen.getCinema().getName() + ", " + screen.getName());
        }
        return screenAndCinema;
    }

    public List<String> allMoviesNames() {
        List<Movie> allMovies = movieRepo.findAll();
        List<String> movieTitle = new LinkedList<>();

        for (Movie movie: allMovies) {
            movieTitle.add(movie.getTitle());
        }

        return movieTitle;

    }
}

