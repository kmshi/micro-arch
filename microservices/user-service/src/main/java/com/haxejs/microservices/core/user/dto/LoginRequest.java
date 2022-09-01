package com.haxejs.microservices.core.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmailOrPhone;
    private String password;
    private Boolean rememberMe = false;
}
