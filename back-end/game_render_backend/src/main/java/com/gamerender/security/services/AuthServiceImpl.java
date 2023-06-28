package com.gamerender.security.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamerender.security.enumerates.UserRole;
import com.gamerender.security.exceptions.MyAPIException;
import com.gamerender.security.models.Role;
import com.gamerender.security.models.SecUser;
import com.gamerender.security.payloads.LoginDto;
import com.gamerender.security.payloads.RegisterDto;
import com.gamerender.security.repositories.RoleRepository;
import com.gamerender.security.repositories.SecUserRepository;
import com.gamerender.security.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private SecUserRepository secUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           SecUserRepository secUserRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.secUserRepository = secUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        
    	Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
        				loginDto.getUsername(), loginDto.getPassword()
        		)
        ); 
    	System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // add check for username exists in database
        if(secUserRepository.existsByUsername(registerDto.getUsername())){
            throw new MyAPIException(HttpStatus.BAD_REQUEST, "This username has already been taken!");
        }

        // add check for email exists in database
        if(secUserRepository.existsByEmail(registerDto.getEmail())){
            throw new MyAPIException(HttpStatus.BAD_REQUEST, "This email is already in use!");
        }

        SecUser secUser = new SecUser();
        secUser.setUsername(registerDto.getUsername());
        secUser.setEmail(registerDto.getEmail());
        secUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        secUser.setFirstname(registerDto.getFirstname());
        secUser.setLastname(registerDto.getLastname());
        Set<Role> roles = new HashSet<>();
        
        if(registerDto.getRoles() != null) {
	        registerDto.getRoles().forEach(role -> {
	        	Role userRole = roleRepository.findByRole(getRole(role)).get();
	        	roles.add(userRole);
	        });
        } else {
        	Role userRole = roleRepository.findByRole(UserRole.ROLE_USER).get();
        	roles.add(userRole);
        }
        
        secUser.setRoles(roles);
        System.out.println(secUser);
        secUserRepository.save(secUser);

        return "User registered successfully!.";
    }
    
    public UserRole getRole(String role) {
    	if(role.equals("ADMIN")) return UserRole.ROLE_ADMIN;
    	else return UserRole.ROLE_USER;
    }
    
}
