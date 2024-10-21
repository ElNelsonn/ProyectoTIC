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
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.SnackService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller("/snack")
public class SnackController {

    @Autowired
    private SnackService snackService;

    @GetMapping("/create")
    public String getCreateSnack() {

        return "snack-create";
    }

    @PostMapping("/create")
    public String crateSnack(@ModelAttribute @Valid Snack snack, BindingResult result, @RequestParam String cinemaName, Model model) {

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("errorMessages", errorMessages);
            return "client-signup";
        }

        try {

            Snack newSnack = snackService.addSnack(
                    snack.getName(),
                    snack.getGlutenFree(),
                    snack.getPrice()
            );

            return "client-signup-success";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("errorMessages", errorMessages);
            return "client-signup";
        }
    }

}
