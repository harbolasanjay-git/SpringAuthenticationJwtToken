package com.stech.api.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stech.api.repository.JwtService;
import com.stech.api.service.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MyUserDetailService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					token = authHeader.substring(7);
					username = jwtService.extractUserName(token);
				}
				if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					UserDetails userdetails = userDetailsService.loadUserByUsername(username);
				try {
				if(jwtService.validateToken(token, userdetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				}
				}catch(ServletException | IOException se) {
					se.printStackTrace();
					throw new RuntimeException("Error occurred during filter chain execution", se);
				}
				}
				 filterChain.doFilter(request, response);
	}

}
