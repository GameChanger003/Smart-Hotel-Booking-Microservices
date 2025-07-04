package com.cts.hotel.security;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.hotel.jwt.JwtAuthenticationFilter;
import com.cts.hotel.jwt.JwtUtil;
 
@Configuration
public class SecurityConfig {
 
    private final JwtUtil jwtUtil;
 
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cs -> cs.disable())
        .cors(cors->cors.configure(http))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register","/auth/test").permitAll()
                .requestMatchers("/admin/rooms/**","/hotels/**","/admin/users").hasRole("ADMIN") 
                .requestMatchers("/users/booking/**","/booking/**","/paymnet/**","/Reviews/**","/user/loyalty/**","/user/redemption/**").hasRole("GUEST")
                .requestMatchers("/Manager/rooms/**","/Manager/hotels/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // ✅ Add JWT filter
 
        return http.build();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
 
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }
}