package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.Repository.AuthUserRepository;
import com.GreetingApp.GreetingApp.model.AuthUser;
import com.GreetingApp.GreetingApp.dto.AuthUserDTO;
import com.GreetingApp.GreetingApp.dto.LoginDTO;
import com.GreetingApp.GreetingApp.exception.UserException;
import com.GreetingApp.GreetingApp.util.EmailSenderService;
import com.GreetingApp.GreetingApp.util.MessageProducer;
import com.GreetingApp.GreetingApp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    AuthUserRepository userRepo;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    MessageProducer messageProducer;

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        AuthUser user = new AuthUser(userDTO);
        userRepo.save(user);
        String token = tokenUtil.createToken(user.getUserId());

        String message = "REGISTER|" + user.getEmail() + "|" + user.getFirstName() + "|" + token;

        messageProducer.sendMessage(message);

        return user;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> user = Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword())) {

            String message = "LOGIN|" + user.get().getEmail() + "|" + user.get().getFirstName();

            messageProducer.sendMessage(message);

            return "Congratulations!! You have logged in successfully!";
        } else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
}


    /*@Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception{
        AuthUser user=new AuthUser(userDTO);
        String token=tokenUtil.createToken(user.getUserId());
        userRepo.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Registered in Greeting App", "Hii...."
                +user.getFirstName()+"\n You have been successfully registered!\n\n Your registered details are:\n\n User Id:  "
                +user.getUserId()+"\n First Name:  "
                +user.getFirstName()+"\n Last Name:  "
                +user.getLastName()+"\n Email:  "
                +user.getEmail()+"\n Address:  "
                +"\n Token:  "+token);
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO){
        Optional<AuthUser> user= Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword()) ){
            emailSenderService.sendEmail(user.get().getEmail(),"Logged in Successfully!", "Hii...."+user.get().getFirstName()+"\n\n You have successfully logged in into Greeting App!");
            return "Congratulations!! You have logged in successfully!";
        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }*/


