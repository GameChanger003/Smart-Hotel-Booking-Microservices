package com.cts.hotel.hotelusers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.hotel.hotelusers.JWT.JwtUtil;
import com.cts.hotel.hotelusers.controller.AuthController;
import com.cts.hotel.hotelusers.model.Role;
import com.cts.hotel.hotelusers.model.User;
import com.cts.hotel.hotelusers.security.SecurityConfig;
import com.cts.hotel.serviceInterface.IUserService;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void login_WithValidCredentials_ReturnsToken() throws Exception {
        String rawPassword = "user";
        String bcryptHash = "$2a$10$kZDrFMYtSWKZ44sTEUFUqeB0ikOl6emLjo7BGXD5KhXQnduV0qaae";

        User user = new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setPassword(bcryptHash);
        user.setRole(Role.GUEST);

        when(userService.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, bcryptHash)).thenReturn(true);
        when(jwtUtil.generateToken("user@gmail.com", 1L, "GUEST")).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "user@gmail.com",
                        "password": "user"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void login_WithWrongPassword_ReturnsUnauthorized() throws Exception {
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("hashed-password");

        when(userService.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed-password")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "user@gmail.com",
                        "password": "wrong"
                    }
                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WithNewEmail_ReturnsSuccess() throws Exception {
        when(userService.findByEmail("new@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "new@example.com",
                        "password": "abc123"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void register_WithExistingEmail_ReturnsConflict() throws Exception {
        when(userService.findByEmail("user@gmail.com")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "email": "user@gmail.com",
                        "password": "abc123"
                    }
                """))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists"));
    }

}
