package com.example.demo.returnCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponse {

    private String SuccessCode;
    private String message;

}
