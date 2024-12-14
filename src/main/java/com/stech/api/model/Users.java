package com.stech.api.model;

import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Getter
@Service
@Table(name = "Users")
public class Users {
	@Id
	private int id;
	private String userName;
	private String password;
}
