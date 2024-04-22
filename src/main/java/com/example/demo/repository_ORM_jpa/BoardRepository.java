package com.example.demo.repository_ORM_jpa;

import com.example.demo.domain_DB.Board;
import com.example.demo.domain_DB.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    Optional<Board> findByIdAndUserId(Long id, User userId);
}
