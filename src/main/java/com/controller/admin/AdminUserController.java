package com.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.UserEntity;
import com.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

	// custom response
	// ResponseEntity -> spring ->

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<UserEntity>> getAllUsers() {

		List<UserEntity> users = userRepository.findAll();
		return ResponseEntity.ok(users);// status:200 ,body:users
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserEntity> getUserByuserId(@PathVariable Integer userId) {
		Optional<UserEntity> op = userRepository.findById(userId);
		if (op.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(op.get());
		}
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteByuserId(@PathVariable Integer userId) {
		Optional<UserEntity> op = userRepository.findById(userId);
		if (op.isEmpty()) {
			HashMap<String, String> map = new HashMap<>();
			map.put("error", "Invalid UserId");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			userRepository.deleteById(userId);
			return ResponseEntity.ok("success");//json  
		}
	}

	// data : modify
	// uniq : userId

	// put vs patch

	@PutMapping("/users/{userId}")
	public String updateUser(@RequestBody UserEntity user, @PathVariable Integer userId) {

		Optional<UserEntity> op = userRepository.findById(userId);

		if (op.isEmpty()) {
			return "Invalid User";
		} else {
			UserEntity dbUser = op.get();
			// white lbl parameter -> allow
			dbUser.setFirstName(user.getFirstName());
			userRepository.save(dbUser);
		}

		return "success";
	}

}
