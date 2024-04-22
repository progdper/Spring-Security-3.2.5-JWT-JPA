package com.example.demo.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String accessPrivate;
    private String accessPublic;

    private String refreshPrivate;
    private String refreshPublic;

    private int accessExpirationMs;

    private int refreshExpirationMs;
}
