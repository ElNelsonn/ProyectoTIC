package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.converters.CinemaConverter;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.ScreenService;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @Autowired
    private CinemaConverter cinemaConverter; //pasas ID y te dice si existe o no

    @GetMapping("/create")
    public String getCreateScreen(Model model){
        model.addAttribute("todayDate", LocalDate.now());
        return "Y" ;//HTML faltante
    }

    @PostMapping("/create")
    public String createScreen(@ModelAttribute @Valid Screen screen,BindingResult result, @RequestParam String cinemaName , Model model) {
        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            model.addAttribute("errorMessages", errorMessages);
            return "Y";
        }
        try {
            Screen newScreen = screenService.addScreen(
                    screen.getName(),
                    cinemaName,
                    screen.getRows(),
                    screen.getColumms()
            );
            return "screen-creation-successful";
        } catch (EntityAlreadyExistsException | InvalidDataException | EntityNotFoundException e) {
            errorMessages.add(e.getMessage());
            model.addAttribute("errorMessages", errorMessages);
            return "Y";
        }
    }
}
