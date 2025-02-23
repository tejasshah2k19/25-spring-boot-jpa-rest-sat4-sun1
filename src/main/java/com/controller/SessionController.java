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
import com.util.JwtUtil;

@RestController
@RequestMapping("/public")
public class SessionController {

	// signup -> post

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenService tokenService;
	
	@Autowired
	JwtUtil jwt;
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
			String token = jwt.generateToken(user.getEmail());
			UserEntity dbUser = op.get();
			dbUser.setToken(token);
			userRepository.save(dbUser);
			return ResponseEntity.status(HttpStatus.OK).header("token", token).body(dbUser);
			
		}
	}

}
