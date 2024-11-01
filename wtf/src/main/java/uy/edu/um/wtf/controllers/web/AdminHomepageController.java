package uy.edu.um.wtf.controllers.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.edu.um.wtf.services.AdministratorService;

@Controller
@RequestMapping("/adminHomepage")
public class AdminHomepageController {

    @GetMapping
    public String showAdminHomePage() {
        return "admin-homepage";
    }

    @GetMapping("/createAdmin")
    public String createAdmin() {
        return "admin-creation";
    }

    @GetMapping("/createSnack")
    public String createSnack() {
        return "snack-creation";
    }

    @GetMapping("/createFunction")
    public String createFunction() {
        return "function-creation";
    }

    @GetMapping("/createMovie")
    public String createMovie() {
        return "movie-creation";
    }
}
