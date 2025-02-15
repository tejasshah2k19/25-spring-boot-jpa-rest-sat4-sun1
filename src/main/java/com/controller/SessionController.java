package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.UserEntity;
import com.repository.UserRepository;
import com.service.TokenService;
//s/w -> rest client -> postman 
//os -> android , ios 

@RestController
@RequestMapping("/public")
public class SessionController {

	// signup -> post

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenService tokenService;
	
	@PostMapping("signup")
	public UserEntity signup(@RequestBody UserEntity user) {
		// validation
		System.out.println("signup api");
		// db save
		userRepository.save(user);

		return user;// json
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody UserEntity user) {

		Optional<UserEntity> op = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (op.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
		} else {
			String token = tokenService.generateToken(16);
			UserEntity dbUser = op.get();
			dbUser.setToken(token);
			userRepository.save(dbUser);
			return ResponseEntity.ok(dbUser);
		}
	}

}
