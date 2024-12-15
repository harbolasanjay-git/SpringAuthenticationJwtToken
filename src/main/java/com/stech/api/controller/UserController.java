package com.stech.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stech.api.dto.AuthRequest;
import com.stech.api.repository.JwtService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@GetMapping("/welcome")
	public String getWelcome() {
		return "Welcome to first authentication successful page !!!";
	}

	@GetMapping("/public")
	@PreAuthorize("hasAuthority('USER')")
	public String getPublicPage() {
		return "Welcome to public page(USER ROLE) !!!";
	}
	
	@GetMapping("/publicAdm")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getAdminPage() {
		return "Welcome to public page(ADMIN ROLE) !!!";
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated())
			return jwtService.generateToken(authRequest.getUsername());
		else
			throw new UsernameNotFoundException("This is an invalid user !!!");
	}

}
