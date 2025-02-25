package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.Repository.GreetingRepository;
import com.GreetingApp.GreetingApp.dto.Greeting;
import com.GreetingApp.GreetingApp.dto.User;
import com.GreetingApp.GreetingApp.exception.ItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService implements IGreetingService {
    private static final String template = "Hello world";
    private final AtomicLong counter = new AtomicLong();
    GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public Greeting greetingMessage() {
        return new Greeting(counter.incrementAndGet(),String.format(template));
    }

    @Override
    public ResponseEntity<User> greetingMessageWithRepo(User user) {
        User savedUser = greetingRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public User getById(@PathVariable  Long id) {
        return greetingRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Override
    public List<User> getAllUsers() {
        return greetingRepository.findAll();
    }

    @Override
    public User updateOrCreate(User newItem, Long id) {
        return greetingRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newItem.getFirstName());
                    user.setLastName(newItem.getLastName());
                    return greetingRepository.save(user);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return greetingRepository.save(newItem);
                });
    }

    @Override
    public void delete(Long id) {
        greetingRepository.deleteById(id);
    }


}
