package com.GreetingApp.GreetingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends  RuntimeException {
    private Long id;

    public ItemNotFoundException(Long id) {
        super("Could not find item " + id);
    }
}
