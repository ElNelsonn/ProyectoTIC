package uy.edu.um.wtf.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.ClientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @GetMapping("/signup")
    public String getLogin(){

        return "client-signup";
    }


    /*
    @PostMapping("/registro")
    public String registerClient(@RequestParam String name, @RequestParam String surname,
                                 @RequestParam String email, @RequestParam String password,
                                 @RequestParam Long identityCard, @RequestParam LocalDate birthDate, Model model) {
        try {

            System.out.println("Intento de sign up.");

            Client newClient = clientService.addClient(
                    identityCard,
                    name,
                    surname,
                    birthDate,
                    null,     // Número de tarjeta se ingresa luego
                    null,                // Fecha de expiración de la tarjeta se ingresa luego
                    email,
                    password
            );

            System.out.println("Sign up.");

            return "client-signup-success";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "client-signup";
        }
    }

     */

    @PostMapping("/registro")
    public String registerClient(@ModelAttribute @Valid Client client, BindingResult result, Model model) {

        if (result.hasErrors()) {

            String errorMessage = result.getFieldErrors().getFirst().getDefaultMessage();

            System.out.println(errorMessage);
            model.addAttribute("errorMessages", errorMessage);
            return "client-signup";
        }

        try {

            System.out.println("Intento de sign up.");

            Client newClient = clientService.addClient(
                    client.getIdentityCard(),
                    client.getName(),
                    client.getSurname(),
                    client.getBirthDate(),
                    null,     // Número de tarjeta se ingresa luego
                    null,                // Fecha de expiración de la tarjeta se ingresa luego
                    client.getEmail(),
                    client.getPassword()
            );

            System.out.println("Sign up.");

            return "client-signup-success";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "client-signup";
        }
    }






}
