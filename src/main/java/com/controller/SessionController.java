package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.UserEntity;
import com.repository.UserRepository;
//s/w -> rest client -> postman 
//os -> android , ios 

@RestController
public class SessionController {

	// signup -> post

	@Autowired
	UserRepository userRepository;

	@PostMapping("signup")
	public UserEntity signup(@RequestBody UserEntity user) {
		// validation

		// db save
		userRepository.save(user);

		return user;// json
	}
}
