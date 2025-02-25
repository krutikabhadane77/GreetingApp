package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.dto.Greeting;
import com.GreetingApp.GreetingApp.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IGreetingService {
    Greeting greetingMessage();

    ResponseEntity<User> greetingMessageWithRepo(@RequestBody User user);

    User getById(@PathVariable Long id);

    List<User> getAllUsers();

    User updateOrCreate(@RequestBody User newItem, @PathVariable Long id);

    public void delete(@PathVariable Long id);


}
