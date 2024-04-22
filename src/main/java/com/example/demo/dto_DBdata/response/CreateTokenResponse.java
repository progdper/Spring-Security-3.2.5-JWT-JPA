package com.example.demo.dto_DBdata.response;

import com.example.demo.domain_DB.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CreateTokenResponse {
    private String refreshToken;
    private User userId;
}
