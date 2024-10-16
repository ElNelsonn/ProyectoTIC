package uy.edu.um.wtf.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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



}
