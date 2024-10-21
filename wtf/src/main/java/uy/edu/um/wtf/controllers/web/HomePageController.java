package uy.edu.um.wtf.controllers.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomePageController {


    @GetMapping("/home")
    public String getHomePage(Model model) {

        model.addAttribute("signed", false);
        return "homepage";
    }













}
