package org.example.wishlist.repository;

import org.example.wishlist.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long > {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}