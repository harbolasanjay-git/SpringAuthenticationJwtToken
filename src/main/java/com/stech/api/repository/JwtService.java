package com.stech.api.repository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	private static final String SECRET = "20I9LFgHqKmmiMKc500xvGTuEizG9KSlV7d9K97qFp2nJmhfeWmk16zmmNVyC2yLCVJOyOL2vXhyeqLGUKubkZWGpxa1V9vkwzrNrZ5ZMgaob2tmWeudO6OP4XbHpKJUVMiCL9uQtWnm7MZUsjywpedZM65biSWSQeRsARyOJlp1MpZVuTWhN3oKnpqW5Zs1JSg450T8yKq2cOKXtbRPrARUmJa6siMgzLOqy8URIqcovcjDMfKaiCXjoGlM8crR";
	
	public String generateToken(String username) {
		Map<String,Object> claims = new HashMap();
		return createToken(claims,username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(username)
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+1000*60*30))
		.signWith(getSignedKey(),SignatureAlgorithm.HS256)
		.compact();
	}

	private Key getSignedKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);		
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
