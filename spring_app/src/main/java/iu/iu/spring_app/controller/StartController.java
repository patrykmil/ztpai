package iu.iu.spring_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    @GetMapping("/")
    public ResponseEntity<String> start() {
        return ResponseEntity.ok("Start page");
    }
}