package spring.ecosystem.rest_api_template.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/saludo")
    public ResponseEntity<String>saludos(){
        return ResponseEntity.ok("holis");
    }

}
