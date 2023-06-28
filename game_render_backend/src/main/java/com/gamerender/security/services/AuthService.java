package com.gamerender.security.services;

import com.gamerender.security.payloads.LoginDto;
import com.gamerender.security.payloads.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
