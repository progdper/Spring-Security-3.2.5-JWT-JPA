package com.example.demo.service_Model;

import com.example.demo.domain_DB.User;
import com.example.demo.dto_DBdata.request.SignRequest;
import com.example.demo.repository_ORM_jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long createUser(SignRequest request) {
        return userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .build()
        ).getId();

    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public String updateUser(String password, Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            return null;
        }
        user.update(bCryptPasswordEncoder.encode(password));

        return "success";

    }

    @Transactional
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if(user == null) {
            return null;
        }
        userRepository.delete(user);

        return "success";

    }

}
