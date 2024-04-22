package com.example.demo.controller_RouteAndController;

import com.example.demo.config.jwt.TokenAuthenticationFilter;
import com.example.demo.config.jwt.TokenProvider;
import com.example.demo.domain_DB.Token;
import com.example.demo.domain_DB.User;
import com.example.demo.dto_DBdata.request.UserPwChange;
import com.example.demo.payload.JwtResponse;
import com.example.demo.returnCode.ErrorResponse;
import com.example.demo.service_Model.AuthService;
import com.example.demo.service_Model.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final AuthService authService;

    public Long verifyToken(HttpServletRequest headers) {
        String findToken = tokenAuthenticationFilter.parseJwt(headers);

        Long userId = tokenProvider.getUserIdFromAccessJwt(findToken);

        return userService.findUser(userId).getId();
    }

    @PostMapping("/info")
    public ResponseEntity<?> readMyInfo(HttpServletRequest headers) {

        Long findUserId = verifyToken(headers);

        User user = userService.findUser(findUserId);

        return  ResponseEntity.ok(user);

    }

    @PatchMapping("/change")
    public ResponseEntity<?> changeMyInfo(HttpServletRequest headers, @Valid @RequestBody UserPwChange userPwChange) {

        if(!Objects.equals(userPwChange.getPassword(), userPwChange.getRePassword())){
            return ResponseEntity.ok(new ErrorResponse("E999", "변경 할 비밀번호를 확인 해 주세요."));
        }

        Long findUserId = verifyToken(headers);

        String change = userService.updateUser(userPwChange.getPassword(), findUserId);

        if(change == null) {
            return ResponseEntity.ok(new ErrorResponse("E999", "비밀번호 변경 중 오류가 발생했습니다."));
        }

        User findUser = userService.findUser(findUserId);

        String createAccessToken = tokenProvider.generateAccessToken(findUserId);

        String createRefreshToken = tokenProvider.generateRefreshToken(findUserId);

        Token findTokenId = authService.getTokenId(findUser);

        authService.updateToken(createRefreshToken, findTokenId.getId());

        return ResponseEntity.ok(new JwtResponse("Bearer", createAccessToken, createRefreshToken, findUser.getId()));
    }

    @DeleteMapping("/del")
    public ResponseEntity<?> delMyInfo(HttpServletRequest headers) {

        Long findUserId = verifyToken(headers);

        String delUser = userService.deleteUser(findUserId);

        if(delUser == null) {
            return ResponseEntity.ok(new ErrorResponse("E999", "비밀번호 변경 중 오류가 발생했습니다."));
        }
        return ResponseEntity.ok("success");
    }

}
