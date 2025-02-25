package com.GreetingApp.GreetingApp.Controller;

import com.GreetingApp.GreetingApp.Repository.GreetingRepository;
import com.GreetingApp.GreetingApp.Service.GreetingService;
import com.GreetingApp.GreetingApp.Service.IGreetingService;
import com.GreetingApp.GreetingApp.dto.Greeting;
import com.GreetingApp.GreetingApp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    }
}








