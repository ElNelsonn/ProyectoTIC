package uy.edu.um.wtf.controllers.web;

import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/")
public class LogInController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private AdministratorConverter administratorConverter;

    @Autowired
    private ClientConverter clientConverter;


    @Controller
    public class LoginController {

        @GetMapping("/login")
        public String loginPage() {
            return "login";
        }
    }


}
