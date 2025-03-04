package com.GreetingApp.GreetingApp.Controller;

import com.GreetingApp.GreetingApp.Repository.GreetingRepository;
import com.GreetingApp.GreetingApp.Service.GreetingService;
import com.GreetingApp.GreetingApp.Service.IGreetingService;
import com.GreetingApp.GreetingApp.dto.Greeting;
import com.GreetingApp.GreetingApp.dto.ResponseDTO;
import com.GreetingApp.GreetingApp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    public GreetingController(GreetingRepository repository) {
        this.repository = repository;
    }

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    GreetingRepository repository;

    @Autowired
    IGreetingService greetingService;

    @GetMapping(value = {"/greeting", "/greeting/", "/greeting/home"})
    public ResponseEntity<ResponseDTO> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        ResponseDTO response = new ResponseDTO("Greeting message generated successfully!", greeting);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("greeting/{name}")
    public ResponseEntity<ResponseDTO> greetings(@PathVariable String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        ResponseDTO response = new ResponseDTO("Personalized greeting generated successfully!", greeting);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("greeting/service")
    public ResponseEntity<ResponseDTO> greeting() {
        Greeting greeting = greetingService.greetingMessage();
        ResponseDTO response = new ResponseDTO("Greeting message retrieved from service!", greeting);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/greeting")
    public ResponseEntity<ResponseDTO> greetingMessageWithRepo(@RequestBody User user) {
        User savedUser = greetingService.greetingMessageWithRepo(user);
        ResponseDTO response = new ResponseDTO("User saved successfully!", savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/greetingById/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        User user = greetingService.getById(id);
        ResponseDTO response = new ResponseDTO("User retrieved successfully!", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/greeting/all")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<User> users = greetingService.getAllUsers();
        ResponseDTO response = new ResponseDTO("All users retrieved successfully!", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/greeting/{id}")
    public ResponseEntity<ResponseDTO> updateOrCreate(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = greetingService.updateOrCreate(user, id);
        ResponseDTO response = new ResponseDTO("User updated successfully!", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("greeting/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        greetingService.delete(id);
        ResponseDTO response = new ResponseDTO("User deleted successfully!", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


    /*@GetMapping(value = {"/greeting", "/greeting/", "/greeting/home"})
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("greeting/{name}")
    public Greeting greetings(@PathVariable String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("greeting/service")
    public Greeting greeting() {
        return greetingService.greetingMessage();
    }

    @PostMapping("/greeting")
    public ResponseEntity<User> greetingMessageWithRepo(@RequestBody User user) {
        return greetingService.greetingMessageWithRepo(user);
    }

    @GetMapping("/greetingById/{id}")
    User getById(@PathVariable Long id) {
        return greetingService.getById(id);
    }

    @GetMapping("/greeting/all")
    List<User> getAllUsers() {
        return greetingService.getAllUsers();
    }


    @PutMapping("/greeting/{id}")
    public User updateOrCreate(@RequestBody User user, @PathVariable Long id) {
        return greetingService.updateOrCreate(user, id);
    }

    @DeleteMapping("greeting/{id}")
    public void delete(@PathVariable Long id) {
         greetingService.delete(id);
    }*/






