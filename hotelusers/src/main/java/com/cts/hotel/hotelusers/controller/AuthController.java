package com.cts.hotel.hotelusers.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.hotelusers.JWT.JwtUtil;
import com.cts.hotel.hotelusers.model.User;
import com.cts.hotel.hotelusers.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
		Optional<User> dbUser = userService.findByEmail(user.getEmail()); // ✅ Authenticate via email
		if (dbUser.isPresent() && passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
			String token = jwtUtil.generateToken(dbUser.get().getEmail(), dbUser.get().getId(),
					dbUser.get().getRole().toString());
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		}
		return ResponseEntity.status(401).body("Invalid credentials");
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		System.out.println(user);
		if(userService.findByEmail(user.getEmail()).isPresent()) {			
			return ResponseEntity.status(409).body("Email already Exists");
		}
			userService.registerUser(user);
			return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
	}

}
