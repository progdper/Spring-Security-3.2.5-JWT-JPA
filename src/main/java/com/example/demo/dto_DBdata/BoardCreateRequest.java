package com.example.demo.dto_DBdata;

import com.example.demo.domain_DB.Board;
import com.example.demo.domain_DB.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardCreateRequest {
    private String title;
    private String content;
    private User userId;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }

}
