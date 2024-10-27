package org.example.wishlist.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String name) {
        super(String.format("User with name %s already exist", name));
    }
}
