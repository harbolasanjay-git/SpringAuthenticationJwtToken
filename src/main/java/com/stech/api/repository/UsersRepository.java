package com.stech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stech.api.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{
	Users findByUserName(String username);
}
