package com.example.demo.dto_DBdata.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTokenRequest {
//    private String accessToken;
    private String refreshToken;
}
