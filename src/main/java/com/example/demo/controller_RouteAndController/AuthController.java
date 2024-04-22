package com.example.demo.controller_RouteAndController;

import com.example.demo.config.jwt.TokenProvider;
import com.example.demo.domain_DB.Token;
import com.example.demo.domain_DB.User;
import com.example.demo.dto_DBdata.request.UpdateTokenRequest;
import com.example.demo.dto_DBdata.request.SignRequest;
import com.example.demo.payload.JwtResponse;
import com.example.demo.returnCode.ErrorResponse;
import com.example.demo.returnCode.SuccessResponse;
import com.example.demo.service_Model.AuthService;
import com.example.demo.service_Model.UserService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignRequest signRequest) {

        Boolean findEmail = authService.findByEmail(signRequest.getEmail());
        if (findEmail) {
            return ResponseEntity.badRequest().body(new ErrorResponse("E999","가입된 email 이 있습니다."));
        }

        Long createId = userService.createUser(signRequest);

        String createAccessToken = tokenProvider.generateAccessToken(createId);

        String createRefreshToken = tokenProvider.generateRefreshToken(createId);

        authService.saveToken(createRefreshToken, createId);

        return ResponseEntity.ok(new JwtResponse("Bearer", createAccessToken, createRefreshToken, createId));

//        return ResponseEntity.ok(new SuccessResponse("E101", "회원가입이 완료되었습니다."));


    }




    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody SignRequest signRequest) {

//        String rePassword = bCryptPasswordEncoder.encode(signRequest.getPassword());

        // User를 직접적으로 사용하지 않기 위해 여러가지 방법을 사용함
//        FindUserResponse findUserResponse = (FindUserResponse) authService.getEmails(signRequest.getEmail());
//        if (findUserResponse == null || !Objects.equals(findUserResponse.getPassword(), rePassword)) {
//            return ResponseEntity.badRequest().body(new ErrorResponse("E999","가입된 email 이 없습니다."));
//        }

//        String findPassword = authService.getEmail(signRequest.getEmail());
//        if (findPassword == null || !Objects.equals(findPassword, rePassword)) {
//            return ResponseEntity.badRequest().body(new ErrorResponse("E999","가입된 email 이 없습니다."));
//        }

        User findUser = authService.getEmails(signRequest.getEmail());
//        if(findUser ==null || !Objects.equals(findUser.getPassword(), rePassword)) {
//            return ResponseEntity.badRequest().body(new ErrorResponse("E999","가입된 email 이 없거나 비밀번호가 틀렸습니다."));
//        }

        if(findUser == null || !bCryptPasswordEncoder.matches(signRequest.getPassword(), findUser.getPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("E999","가입된 email 이 없거나 비밀번호가 틀렸습니다."));
        }

        String createAccessToken = tokenProvider.generateAccessToken(findUser.getId());

        String createRefreshToken = tokenProvider.generateRefreshToken(findUser.getId());

        Token findTokenId = authService.getTokenId(findUser);

        if(findTokenId == null) {
            authService.saveToken(createRefreshToken, findUser.getId());
        } else {
            authService.updateToken(createRefreshToken, findTokenId.getId());
        }

        return ResponseEntity.ok(new JwtResponse("Bearer", createAccessToken, createRefreshToken, findUser.getId()));

    }


    @PatchMapping("/updatetoken")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UpdateTokenRequest updateTokenRequest) {

        Claims requestPayload = tokenProvider.getJtiFromRefreshJwt(updateTokenRequest.getRefreshToken());

        User findUserId = userService.findUser(requestPayload.get("id", Long.class));

        Token findToken = authService.getTokenId(findUserId);

        Claims findPayLoad = tokenProvider.getJtiFromRefreshJwt(findToken.getRefreshToken());

        if(!Objects.equals(requestPayload.get("jti", String.class), findPayLoad.get("jti", String.class))){
            return ResponseEntity.ok(new ErrorResponse("E101", "refreshToken이 손상되었습니다."));
        }

        String createAccessToken = tokenProvider.generateAccessToken(requestPayload.get("id", Long.class));

        String createRefreshToken = tokenProvider.generateRefreshToken(requestPayload.get("id", Long.class));

        authService.updateToken(createRefreshToken, findToken.getId());

        return ResponseEntity.ok(new JwtResponse("Bearer", createAccessToken, createRefreshToken, findToken.getId()));

    }






}
