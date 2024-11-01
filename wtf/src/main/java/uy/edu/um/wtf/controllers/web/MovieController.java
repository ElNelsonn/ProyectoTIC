package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.edu.um.wtf.DTO.MovieForController;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.MovieService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/add")
    public String getAddMovie(Model model) {
        // Definir los datos de la película que se crearán por defecto
        String title = "Película por defecto";
        LocalDate releaseDate = LocalDate.now();
        List<String> directors = Arrays.asList("Director 1", "Director 2");
        String synopsis = "Sinopsis de ejemplo";
        List<String> categories = Arrays.asList("Acción", "Drama");
        List<String> actors = Arrays.asList("Actor 1", "Actor 2");
        long duration = 120;
        String classification = "10";
        String posterURL = "https://m.media-amazon.com/images/M/MV5BMjA5NDQyMjc2NF5BMl5BanBnXkFtZTcwMjg5ODcyMw@@._V1_.jpg";

        try {
            // Crear la nueva película con los datos por defecto
            Movie newMovie = movieService.addMovie(
                    title,
                    releaseDate,
                    directors,
                    synopsis,
                    categories,
                    actors,
                    duration,
                    classification,
                    posterURL
            );
            model.addAttribute("successMessage", "Película creada exitosamente.");
            return "client-signup-success"; // Redirigir a una página de éxito o donde prefieras
        } catch (EntityAlreadyExistsException | InvalidDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "movie-creation"; // Redirigir a la página de creación si ocurre un error
        }
    }






//    public String getAddMovie(Model model) {
//        return "movie-creation";
//    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute @Valid MovieForController movie, BindingResult result, Model model) {

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
                    movie.getPosterURL()
            );

            return "client-signup-success";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "movie-creation";
        }

    }









}
