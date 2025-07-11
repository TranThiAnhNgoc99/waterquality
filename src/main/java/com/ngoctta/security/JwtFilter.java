package com.ngoctta.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ngoctta.services.UserServiceImpl;

public class JwtFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired 
	private UserServiceImpl userServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
		
	}

	private String parseJwt(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
		
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7, header.length());
		}
		return null;
	}

}
