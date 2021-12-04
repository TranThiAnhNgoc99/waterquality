package com.ngoctta.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ngoctta.entity.MyUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

	private String jwtSecret = "Spring01";
	private int jwtExpirationMs = 86400000;
	
	public String generateJwtToken(Authentication authentication) {
		
		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(myUserDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid Jwt signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid Jwt token: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("Jwt token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Jwt claims string is empty: {}", e.getMessage());
		}
		
		return false;
	}
}