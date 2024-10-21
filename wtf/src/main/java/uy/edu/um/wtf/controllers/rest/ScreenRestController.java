package uy.edu.um.wtf.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.services.ScreenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Screen")
public class ScreenRestController {

    @Autowired
    private ScreenService screenService;

    @RequestMapping("/")
    public ResponseEntity<List<Screen>> getAll(){return ResponseEntity.ok(screenService.allScreens());}

}
