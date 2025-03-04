package com.GreetingApp.GreetingApp.Controller;

import com.GreetingApp.GreetingApp.Service.AuthenticationService;
import com.GreetingApp.GreetingApp.dto.AuthUserDTO;
import com.GreetingApp.GreetingApp.dto.LoginDTO;
import com.GreetingApp.GreetingApp.dto.ResponseDTO;
import com.GreetingApp.GreetingApp.model.AuthUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthUserController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@Valid @RequestBody AuthUserDTO userDTO, @PathVariable String email){
        AuthUser user=authenticationService.forgotPassword(userDTO,email);
        ResponseDTO responseUserDTO =new ResponseDTO("Password has been changed successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String email,
                                                     @RequestParam String currentPassword,
                                                     @RequestParam String newPassword) {
        String message = authenticationService.resetPassword(email, currentPassword, newPassword);
        ResponseDTO responseUserDTO = new ResponseDTO();
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }


}
