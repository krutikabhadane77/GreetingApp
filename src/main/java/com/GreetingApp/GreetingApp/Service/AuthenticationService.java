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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        AuthUser user = new AuthUser(userDTO);
        user.setPassword(hashedPassword);  // Store hashed password
        userRepo.save(user);
        String token = tokenUtil.createToken(user.getUserId());
        String message = "REGISTER|" + user.getEmail() + "|" + user.getFirstName() + "|" + token;
        messageProducer.sendMessage(message);
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> user = Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(),user.get().getPassword())) {
            String message = "LOGIN|" + user.get().getEmail() + "|" + user.get().getFirstName();
            messageProducer.sendMessage(message);
            return "Congratulations!! You have logged in successfully!";
        }
        else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }

    @Override
    public AuthUser forgotPassword(AuthUserDTO userDTO, String email){
        AuthUser user=userRepo.findByEmail(email);
        if (user!=null){
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(hashedPassword);
            userRepo.save(user);
            emailSenderService.sendEmail(user.getEmail(),"Password Updated", "Hii...."+user.getFirstName()+" Your Password has been updated!\n\n Your New password: "+user.getPassword());
            return user;
        }else {
            throw new UserException("Sorry! We can not find the user email: "+email);
        }
    }

    @Override
    public String resetPassword(String email, String currentPassword, String newPassword) {
        // Find user by email
        AuthUser user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        // Check if the current password matches the stored password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserException("Current password is incorrect!");
        }

        // Hash and update the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return "Password reset successfully!";
    }


    /*@Override
    public void resetPassword(String token, String newPassword) {
        AuthUser user = userRepo.findByResetToken(token);
        if (user == null) {
            throw new UserException("Invalid or expired token!");
        }
        // Hash the new password
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        // Clear the token after reset
        user.setResetToken(null);
        userRepo.save(user);
    }*/
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


