package uy.edu.um.wtf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.SnackPurchase;
import uy.edu.um.wtf.services.SnackPurchaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/SnackPurchase")
public class SnackPurchaseRestController {

    @Autowired
    private SnackPurchaseService snackPurchaseService;

    @RequestMapping("/")
    public ResponseEntity<List<SnackPurchase>> getAll(){return ResponseEntity.ok(snackPurchaseService.allSnackPurchases());}
}
