package com.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.dto.LoginRequest;
import com.login.dto.RegisterRequest;
import com.login.entity.Utente;
import com.login.service.UtenteService;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "http://localhost:3000")
public class UtenteController {
    
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);
    private final UtenteService utenteService;
    
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    
    @PostMapping("/registra")
    public ResponseEntity<?> registra(@RequestBody RegisterRequest request) {
        logger.info("Ricevuta richiesta di registrazione per username: {}", request.getUsername());
        try {
            utenteService.registra(request);
            return ResponseEntity.ok("Registrazione completata con successo");
        } catch (Exception e) {
            logger.error("Errore durante la registrazione", e);
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Ricevuta richiesta di login per username: {}", request.getUsername());
        try {
            Utente utente = utenteService.login(request);
            return ResponseEntity.ok(utente);
        } catch (Exception e) {
            logger.error("Errore durante il login", e);
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
}