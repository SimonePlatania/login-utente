package com.login.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.login.dto.LoginRequest;
import com.login.dto.RegisterRequest;
import com.login.entity.Utente;
import com.login.mapper.UtenteMapper;

@Service
public class UtenteService {

	private final UtenteMapper utenteMapper;
	private final PasswordEncoder passwordEncoder;

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
}