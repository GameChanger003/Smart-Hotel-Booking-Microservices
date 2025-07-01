package com.cts.hotel.hotelusers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.hotelusers.model.Role;
import com.cts.hotel.hotelusers.model.User;
import com.cts.hotel.hotelusers.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String getAdminDashboard() {
		return "Welcome to Admin Dashboard!";
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAdminAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/users/Managers")
	public ResponseEntity<List<User>> getAdminManagerUsers() {
		List<User> users = userService.getUserByRoles(Role.MANAGER);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/users/Guests")
	public ResponseEntity<List<User>> getAdminGuestUsers() {
		List<User> users = userService.getUserByRoles(Role.GUEST);
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/users/")
	public ResponseEntity<String> deleteUser(@RequestBody Map<String, Integer> request) {
		//send the id through body		
		Integer id = request.get("id");
		if (id == null) {
			return ResponseEntity.badRequest().body("User ID is required!");
		}

		userService.removeUsers(id);
		return ResponseEntity.ok("User deleted successfully!");
	}

}
