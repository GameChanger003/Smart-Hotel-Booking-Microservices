package com.cts.hotel.hotelusers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.hotel.hotelusers.model.Role;
import com.cts.hotel.hotelusers.model.User;
import com.cts.hotel.hotelusers.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty"); 
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        userRepository.save(user);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
   public List<User> getAllUsers(){
	   return userRepository.findAll();
   }
   
   public List<User> getUserByRoles(Role role) {
       return userRepository.findByRole(role);
   }
   
   public void removeUsers(int id) {
	   if (!userRepository.existsById(id)) {
           throw new RuntimeException("User not found with ID: " + id);
       }
       userRepository.deleteById(id);
   }
}
