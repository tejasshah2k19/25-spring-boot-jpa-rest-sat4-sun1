package com.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.entity.UserEntity;
import com.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{userId}")
	public UserEntity getUserByuserId(@PathVariable Integer userId) {
		Optional<UserEntity> op = userRepository.findById(userId);
		if (op.isEmpty()) {
			return null;
		} else {
			return op.get();
		}
	}

	@DeleteMapping("/users/{userId}")
	public String deleteByuserId(@PathVariable Integer userId) {
		Optional<UserEntity> op = userRepository.findById(userId);
		if (op.isEmpty()) {
			return null;
		} else {
			userRepository.deleteById(userId);
			return "success";
		}
	}

}
