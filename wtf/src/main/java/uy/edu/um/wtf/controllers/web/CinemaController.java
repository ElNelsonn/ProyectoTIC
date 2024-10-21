package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("todayDate", LocalDate.now());
        return "X"; //HTML faltante
    }

    @PostMapping("/create")
    public String createCinema(@ModelAttribute @Valid Cinema cinema, BindingResult result, Model model){
        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            model.addAttribute("errorMessages", errorMessages);
            return "X";
        }
        try {
            Cinema newCinema = cinemaService.addCinema(
                    cinema.getName(),
                    cinema.getPhoneNumber(),
                    cinema.getLocation(),
                    cinema.getMail()
            );
            return "cinema-creation-success";
        } catch (EntityAlreadyExistsException | InvalidDataException e) {
            errorMessages.add(e.getMessage());
            model.addAttribute("errorMessages",errorMessages);
            return "X";
        }
    }

}
