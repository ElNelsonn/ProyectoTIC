package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/User")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ResponseEntity<List<User>> getAll(){return ResponseEntity.ok(userService.allUsers());}

}
