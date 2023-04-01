package com.fullStackApp.EmpManagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtTokenProveder jwtTokenProveder;
	
	public CustomUserDetailService getCustomUserDetailService() {
		return customUserDetailService;
	}

	public void setCustomUserDetailService(CustomUserDetailService customUserDetailService) {
		this.customUserDetailService = customUserDetailService;
	}

	public JwtTokenProveder getJwtTokenProveder() {
		return jwtTokenProveder;
	}

	public void setJwtTokenProveder(JwtTokenProveder jwtTokenProveder) {
		this.jwtTokenProveder = jwtTokenProveder;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// get token from Header
		
		String token = getTokenFromHeader(request);
		
		// check valid token
		
		if(StringUtils.hasText(token) && jwtTokenProveder.validateToken(token)) {
			String email = jwtTokenProveder.getEmailFromToken(token);
			UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
 		}
		// set Authentication authorization
		filterChain.doFilter(request, response);
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer")) {
			
			return token.substring(7, token.length());
		}
		return null;
	}

}
