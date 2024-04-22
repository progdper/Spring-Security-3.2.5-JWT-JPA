package com.example.demo.dto_DBdata.request;

import com.example.demo.domain_DB.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardRequest {

    private String title;
    private String content;


}
