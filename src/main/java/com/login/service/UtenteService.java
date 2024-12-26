package com.login.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.login.controller.UtenteController;
import com.login.dto.LoginRequest;
import com.login.dto.ProfiloUpdateRequest;
import com.login.dto.RegisterRequest;
import com.login.entity.Utente;
import com.login.mapper.UtenteMapper;

@Service
public class UtenteService {

	private final UtenteMapper utenteMapper;
	private final PasswordEncoder passwordEncoder;
	private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

	public UtenteService(UtenteMapper utenteMapper, PasswordEncoder passwordEncoder) {
		this.utenteMapper = utenteMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void registra(RegisterRequest request) {
		validateRegistration(request);

		// Crea nuovo utente
		Utente utente = new Utente();
		utente.setUsername(request.getUsername());
		utente.setEmail(request.getEmail());
		utente.setPassword(passwordEncoder.encode(request.getPassword()));
		utente.setRuolo("USER");
		utente.setDataCreazione(LocalDateTime.now());

		// Salva l'utente
		utenteMapper.insert(utente);
	}

	public void validateRegistration(RegisterRequest request) {

		if (utenteMapper.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username già in uso");
		}

		// Verifica se email già esistente
		if (utenteMapper.findByEmail(request.getEmail()) != null) {
			throw new RuntimeException("Email già registrata");
		}

		if (!isValidPassword(request.getPassword())) {
			throw new RuntimeException("Password non valida: minimo 6 caratteri, una maiuscola e una minuscola");
		}

	}

	public boolean isValidPassword(String password) {

		return password.length() >= 6 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*");

	}

	@Transactional
	public Utente login(LoginRequest request) {
		Utente utente = utenteMapper.findByUsername(request.getUsername());
		if (utente == null) {
			throw new RuntimeException("Email non trovata");
		}

		if (!passwordEncoder.matches(request.getPassword(), utente.getPassword())) {
			throw new RuntimeException("Password non valida");
		}

		return utente;
	}

	@Transactional
	public Utente aggiornaUtente(Long id, ProfiloUpdateRequest request) {
	    logger.info("Inizio aggiornamento utente con ID: {}", id);
	    
	    // Recupera utente esistente
	    Utente utente = utenteMapper.findById(id);
	    if (utente == null) {
	        logger.error("Utente non trovato con ID: {}", id);
	        throw new RuntimeException("Utente non trovato con ID: " + id);
	    }
	    
	    // Flag per tracciare se ci sono modifiche
	    boolean modificheEffettuate = false;
	    
	    // Verifica username
	    if (!request.getUsername().equals(utente.getUsername())) {
	        logger.info("Richiesta modifica username da '{}' a '{}'", utente.getUsername(), request.getUsername());
	        if (utenteMapper.existsByUsername(request.getUsername())) {
	            logger.error("Username '{}' già in uso", request.getUsername());
	            throw new RuntimeException("Username già in uso");
	        }
	        utente.setUsername(request.getUsername());
	        modificheEffettuate = true;
	    }
	    
	    // Verifica email
	    if (!request.getEmail().equals(utente.getEmail())) {
	        logger.info("Richiesta modifica email da '{}' a '{}'", utente.getEmail(), request.getEmail());
	        if (utenteMapper.findByEmail(request.getEmail()) != null) {
	            logger.error("Email '{}' già in uso", request.getEmail());
	            throw new RuntimeException("Email già in uso");
	        }
	        utente.setEmail(request.getEmail());
	        modificheEffettuate = true;
	    }

	    // Aggiorna solo se ci sono state modifiche
	    if (modificheEffettuate) {
	        logger.info("Salvataggio modifiche per utente ID: {}", id);
	        utenteMapper.updateUtente(utente);
	        logger.info("Aggiornamento completato con successo");
	    } else {
	        logger.info("Nessuna modifica necessaria per utente ID: {}", id);
	    }
	    
	    return utente;
	}
}