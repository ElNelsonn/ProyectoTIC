package uy.edu.um.wtf.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.ClientService;

@Controller
@RequestMapping("/client")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @GetMapping("/signup")
    public String getLogin(){
        return "clientsignup";
    }
    @PostMapping("/registro")
    public String clientSignUp(@RequestBody Client client) {
        try {

            clientService.addClient(client.getIdentityCard(), client.getName(), client.getSurname(), client.getBirthDate(), null, null, client.getEmail(), client.getPassword());

            return "redirect:/client/success";

        } catch (EntityAlreadyExistsException e) {

            return "clientsignup";

        } catch (InvalidDataException e) {

            return "clientsignup";
        }
    }






}
