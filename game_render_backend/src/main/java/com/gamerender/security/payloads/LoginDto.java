package com.gamerender.security.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;
}

// this is the correct payload for login 
/*{
    "username": "mario.rossi",
    "password": "qwerty"
}*/
