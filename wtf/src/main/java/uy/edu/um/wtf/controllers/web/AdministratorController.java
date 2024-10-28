package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.AdministratorService;
import uy.edu.um.wtf.services.ClientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {


    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/signup")
    public String getLogin(Model model){

        model.addAttribute("todayDate", LocalDate.now());
        return "admin-signup";
    }

    @GetMapping("/homepage")
    public String adminHomepage(){
        return "admin-homepage";
    }

    @PostMapping("/signup")
    public String registerClient(@ModelAttribute @Valid Administrator admin, BindingResult result, @RequestParam String password2, Model model) {

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            System.out.println(errorMessages);

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "admin-signup";
        }

        if (!password2.equals(admin.getPassword())) {

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", "Las contraseñas no coinciden.");
            return "admin-signup";
        }

        try {

            System.out.println("Intento de sign up.");

            Administrator newAdmin = administratorService.addAdministrator(
                    admin.getIdentityCard(),
                    admin.getName(),
                    admin.getSurname(),
                    admin.getBirthDate(),
                    admin.getEmail(),
                    admin.getPassword()
            );

            System.out.println("Sign up exitoso.");

            return "redirect:/client/signup";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {


            errorMessages.add(e.getMessage());

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "admin-signup";
        }
    }






}
