package com.example.demo.dto_DBdata.response;

import com.example.demo.domain_DB.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//public class FindUserResponse extends User {
public class FindUserResponse {
    private Long id;
    private String email;
    private String password;

}
