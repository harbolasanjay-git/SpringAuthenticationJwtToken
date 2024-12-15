package com.stech.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stech.api.dto.UserPrincipal;
import com.stech.api.model.Users;
import com.stech.api.repository.UsersRepository;

@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	UsersRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = repository.findByUserName(username);
		return new UserPrincipal(user);
	}
}
