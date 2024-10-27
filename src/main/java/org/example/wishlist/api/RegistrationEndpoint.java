package org.example.wishlist.api;

import lombok.extern.slf4j.Slf4j;
import org.example.wishlist.service.exception.UserAlreadyExistException;
import org.example.wishlist.service.UserService;
import org.example.wishlist.service.model.UserFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.example.wishlist.config.AppConstant.*;

@Slf4j
@Controller
@RequestMapping(value = REGISTER_PAGE)
public class RegistrationEndpoint {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String register() {
        return REGISTER_PAGE;
    }

    @PostMapping()
    public String userRegistration(final @Valid UserFormData userFormData, final BindingResult bindingResult, final Model model) {
        log.info("Registration new user {}", userFormData.getEmail());
        if (bindingResult.hasErrors()) {
            model.addAttribute(REGISTRATION_ATTRIBUTE, userFormData);
            return REGISTER_PAGE;
        }
        try {
            userService.register(userFormData);
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
            model.addAttribute(REGISTRATION_ATTRIBUTE, userFormData);
            return REGISTER_PAGE;
        }
        return  REDIRECT + LOGIN_PAGE;
    }
}
