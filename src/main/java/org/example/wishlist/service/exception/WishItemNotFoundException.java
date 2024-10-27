package org.example.wishlist.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishItemNotFoundException extends RuntimeException {
    public WishItemNotFoundException(Long id) {
        super(String.format("WishItem with id %s not found", id));
    }
}
