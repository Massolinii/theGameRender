package com.gamerender.security.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.models.User;
import com.gamerender.repositories.UserRepository;
import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.models.Role;
import com.gamerender.security.payloads.JWTAuthResponse;
import com.gamerender.security.payloads.LoginDto;
import com.gamerender.security.payloads.RegisterDto;
import com.gamerender.security.services.AuthService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = { "/login", "/signin" })
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);
        User user = userRepository.findByUsername(loginDto.getUsername())
        		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginDto.getUsername()));

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setUsername(loginDto.getUsername());
        jwtAuthResponse.setAccessToken(token);
        
        Set<UserRole> roles = user.getRoles().stream()  
                .map(Role::getRole)
                .collect(Collectors.toSet());
        
        jwtAuthResponse.setRoles(roles);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = { "/register", "/signup" })
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
