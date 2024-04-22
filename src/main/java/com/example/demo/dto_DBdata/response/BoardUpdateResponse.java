package com.example.demo.dto_DBdata.response;

import com.example.demo.domain_DB.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardUpdateResponse {
    private String title;
    private String content;
    private Long userId;
}
