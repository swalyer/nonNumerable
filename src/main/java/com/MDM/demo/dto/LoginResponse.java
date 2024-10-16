package com.MDM.demo.dto;

import com.MDM.demo.entity.User;

public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
