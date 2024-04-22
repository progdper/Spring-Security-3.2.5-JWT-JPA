package com.example.demo.repository_ORM_jpa;

import com.example.demo.domain_DB.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

}
