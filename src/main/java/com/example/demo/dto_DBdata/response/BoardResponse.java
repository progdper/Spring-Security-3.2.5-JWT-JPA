package com.example.demo.dto_DBdata.response;

import com.example.demo.domain_DB.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardResponse(Board board) {
        this.id= board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();

    }

}
