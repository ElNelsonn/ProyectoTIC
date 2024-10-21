package uy.edu.um.wtf.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.wtf.converters.AdministratorConverter;
import uy.edu.um.wtf.converters.ClientConverter;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.services.AdministratorService;
import uy.edu.um.wtf.services.ClientService;

@Controller
@RequestMapping("/user")
public class LogInController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AdministratorConverter administratorConverter;

    @Autowired
    private ClientConverter clientConverter;


    @GetMapping("/login")
    public String getLogIn() {

        return "login";
    }

    @PostMapping("/login")
    public String logInClient(@RequestParam String email, @RequestParam String password, Model model) {

        Client possibleClient = clientConverter.convert(email);

        if (possibleClient != null) {

            if (!possibleClient.getPassword().equals(password)) {

                model.addAttribute("errorMessages", "Contraseña incorrecta.");
                return "login";
            }


            return "client-signup-success";


        }

        Administrator possibleAdministrator = administratorConverter.convert(email);

        if (possibleAdministrator != null) {

            if (!possibleAdministrator.getPassword().equals(password)) {

                model.addAttribute("errorMessages", "Contraseña incorrecta.");
                return "login";
            }

            return "client-signup-success";

        }


        model.addAttribute("errorMessages", "Usuario no encontrado.");
        return "login";
    }



}
