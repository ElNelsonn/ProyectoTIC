package uy.edu.um.wtf.controllers.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ticket")
public class TicketPurchaseController {

    @PostMapping("/purchase")
    public String getTicketPurchase(@RequestParam String title, @RequestParam String function,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario ) {


        


        return "efw";
    }

    @PostMapping("/purchased")
    public String ticketPurchase(@RequestParam String seat, @RequestParam String movieScreening, @RequestParam String moiveTitle) {


        return "sdf";
    }






}
