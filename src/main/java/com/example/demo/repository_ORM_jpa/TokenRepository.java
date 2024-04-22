package com.example.demo.repository_ORM_jpa;

import com.example.demo.domain_DB.Token;
import com.example.demo.domain_DB.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
//    Optional<Token> findByUserId(User userId);
    Token findByUserId(User user);
}
