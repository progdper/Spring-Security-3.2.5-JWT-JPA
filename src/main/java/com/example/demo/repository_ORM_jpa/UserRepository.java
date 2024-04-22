package com.example.demo.repository_ORM_jpa;

import com.example.demo.domain_DB.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Option은 비싸기 때문에 과도하게 사용하지 말아야 한다.
//     Optional<User> findByEmail(String email);

    User findByEmail(String email);


}

