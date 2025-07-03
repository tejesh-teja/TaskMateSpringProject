package com.tejesh.TaskMate.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class JwtAuthResponse {

    private String token;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String token) {
        this.token=token;
    }
}
