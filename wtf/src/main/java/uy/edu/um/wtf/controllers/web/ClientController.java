package uy.edu.um.wtf.controllers.web;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.services.ClientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepo;

    @GetMapping("/signup")
    public String getSignUp(Model model){

        model.addAttribute("todayDate", LocalDate.now());
        return "client-signup";
    }

    @PostMapping("/signup")
    public String signUpClient(@ModelAttribute @Valid Client client, BindingResult result, @RequestParam String password2, Model model) {

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "client-signup";
        }

        if (!password2.equals(client.getPassword())) {

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", "Las contraseñas no coinciden.");
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

            System.out.println("Sign up exitoso.");

            return "redirect:/login";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "client-signup";
        }
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario, Model model) {

        Optional<Client> client = clientRepo.findClientByEmail(usuario.getUsername());

        if (client.isEmpty()) {

            return "login";
        }

        model.addAttribute("client", client.get());
        model.addAttribute("todayDate", LocalDate.now());
        return "client-profile";
    }


    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam(required = false) String cardNumber, @RequestParam(required = false) LocalDate cardExpirationDate,
                                @RequestParam(required = false) String password,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario, Model model) {

        try {

            clientService.editClient(usuario.getUsername(), cardNumber, cardExpirationDate, password);

            return "redirect:/client/profile";

        } catch (EntityNotFoundException | InvalidDataException e) {

            Optional<Client> client = clientRepo.findClientByEmail(usuario.getUsername());

            List<String> errorMessages = new ArrayList<>();

            if (client.isEmpty()) {

                return "redirect:/login";
            }

            errorMessages.add(e.getMessage());

            model.addAttribute("client", client.get());
            model.addAttribute("todayDate", LocalDate.now());
            model.addAttribute("errorMessages", errorMessages);
            return "client-profile";

        }

    }













}
