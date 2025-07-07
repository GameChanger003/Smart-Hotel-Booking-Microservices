package com.cts.hotel.serviceInterface;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.hotel.hotelusers.model.Role;
import com.cts.hotel.hotelusers.model.User;

@Service
public interface IUserService {

	void registerUser(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

	List<User> getAllUsers();

	List<User> getUserByRoles(Role role);

	void removeUsers(int id);
}
