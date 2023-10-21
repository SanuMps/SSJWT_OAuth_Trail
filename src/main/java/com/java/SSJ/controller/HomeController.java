package com.java.SSJ.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Public endpoint, no authentication required!");
    }

    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Secured endpoint, authentication required!");
    }
}
