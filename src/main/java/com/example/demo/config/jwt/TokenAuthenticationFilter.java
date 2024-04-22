package com.example.demo.config.jwt;

import com.example.demo.returnCode.SuccessResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    // 토큰 인증이 가능하게 만들어 줌 - SecurityContextHolder 에 User 를 등록하여 각 api 에서
    // user 를 점검하는 경우를 제외하고자 하였지만 방법을 못 찾았음
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        String jwt = parseJwt(request);
        System.out.println(tokenProvider.validToken(jwt));
        if (jwt != null && tokenProvider.validToken(jwt)) {
            final Long userId = tokenProvider.getUserIdFromAccessJwt(jwt);
//            System.out.println("==========");
//            System.out.println(userId);
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userId, null);
//            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else if (jwt != null && !tokenProvider.validToken(jwt)) {
            throw new ServletException("토큰의 유효시간이 만료되었습니다.");   // 토큰의 유효시간 처리 방법 확인 필요
        }

        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HEADER_AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

}

