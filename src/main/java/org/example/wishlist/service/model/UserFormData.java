package org.example.wishlist.service.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
public class UserFormData implements Serializable {

    @NotEmpty(message = "Username can not be empty")
    private String username;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email id")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    public UserFormData() {
    }

    public UserFormData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

