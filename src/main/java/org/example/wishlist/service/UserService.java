package org.example.wishlist.service;

import org.example.wishlist.model.UserEntity;
import org.example.wishlist.repository.UserRepository;
import org.example.wishlist.service.exception.UserAlreadyExistException;
import org.example.wishlist.service.model.UserFormData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserFormData user) throws UserAlreadyExistException {
        if (checkIfUserExist(user.getEmail(), user.getUsername())) {
            throw new UserAlreadyExistException(user.getUsername());
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setActive(true);
        encodePassword(userEntity, user);
        userRepository.save(userEntity);
    }

    private boolean checkIfUserExist(String email, String username) {
        return userRepository.findByEmail(email) != null || userRepository.findByUsername(username) != null;
    }

    private void encodePassword(UserEntity userEntity, UserFormData user) {
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}