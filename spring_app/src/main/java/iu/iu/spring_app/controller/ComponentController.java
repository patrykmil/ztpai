package iu.iu.spring_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComponentController {
    @GetMapping("/component/{id}")
    public ResponseEntity<String> getComponent(@PathVariable int id) {
        return ResponseEntity.ok("Component " + id);
    }
}
