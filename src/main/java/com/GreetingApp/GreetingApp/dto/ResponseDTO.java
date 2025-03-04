package com.GreetingApp.GreetingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object object;

    /*public ResponseDTO(String message, Object object) {
        this.message = message;
        this.object = object;
    }

    public ResponseDTO() {

    }*/
}
