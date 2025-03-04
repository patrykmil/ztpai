package iu.iu.spring_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowseController {
    @GetMapping("/browse")
    public ResponseEntity<String> browse() {
        return ResponseEntity.ok("Browse page");
    }
}
