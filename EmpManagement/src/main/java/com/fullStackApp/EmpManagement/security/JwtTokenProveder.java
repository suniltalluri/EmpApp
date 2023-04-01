package com.fullStackApp.EmpManagement.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProveder {
	
	public String generateToken(Authentication authentication) {
		String email = authentication.getName();
		Date currentDate = new Date();
		Date ExpiryDate =  new Date(currentDate.getTime() + 3600000); // milliseconds
		
		String token = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(currentDate)
				.setExpiration(ExpiryDate)
				.signWith(SignatureAlgorithm.HS512,"JwtSecretKey")
				.compact();
		return token;
	}
	
	public String getEmailFromToken(String token) {
		Claims claims =  Jwts.parser().setSigningKey("JwtSecretKey")
		.parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey("JwtSecretKey")
			.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
