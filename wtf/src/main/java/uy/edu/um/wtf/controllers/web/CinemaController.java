package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.CinemaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/create")
    public String getCreateCinema(Model model){

        return "cinema-creation";
    }

    @PostMapping("/create")
    public String createCinema(@ModelAttribute @Valid Cinema cinema, BindingResult result, Model model, RedirectAttributes redirectAttributes){

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("errorMessages", errorMessages);
            return "cinema-creation";

        }
        try {
            Cinema newCinema = cinemaService.addCinema(
                    cinema.getName(),
                    cinema.getPhoneNumber(),
                    cinema.getLocation(),
                    cinema.getEmail()
            );


            redirectAttributes.addFlashAttribute("message", "Cine creado con exito.");
            return "redirect:/adminHomepage";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("errorMessages",errorMessages);
            return "cinema-creation";
        }
    }







}
