package com.example.demo.service_Model;

import com.example.demo.domain_DB.Token;
import com.example.demo.domain_DB.User;
import com.example.demo.repository_ORM_jpa.AuthRepository;
import com.example.demo.repository_ORM_jpa.TokenRepository;
import com.example.demo.repository_ORM_jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public Boolean findByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

    public void saveToken(String refreshToken, Long userId) {
        tokenRepository.save(
                Token.builder()
                .refreshToken(refreshToken)
                        .userId(userRepository.findById(userId).get()).build()
        );
    }

//    public String getEmail (String email) {
//        return userRepository.findByEmail(email).getPassword();
//    }
//
    public User getEmails (String email) {
        return userRepository.findByEmail(email);
    }

    public Token getTokenId (User user) {
        return tokenRepository.findByUserId(user);
    }

    @Transactional  // 트랙잭션으로 영속성을 유지해야 jpa로 업데이트가 가능하다.
    public void updateToken(String refreshToken, Long tokenId) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + tokenId));

        token.update(refreshToken);
    }





//    public Optional<Token> getUserToken(User userId) {
//        return tokenRepository.findByUserId(userId);
//    }


}
