package com.example.nwp_projekat.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
