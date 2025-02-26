package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.model.AuthUser;
import com.GreetingApp.GreetingApp.dto.AuthUserDTO;
import com.GreetingApp.GreetingApp.dto.LoginDTO;

public interface IAuthenticationService {

    AuthUser register(AuthUserDTO userDTO) throws Exception;

    String login(LoginDTO loginDTO);
}
