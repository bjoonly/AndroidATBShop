package com.example.app2803.account.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    public String firstName;
    public String secondName;
    public String email;
    public String phone;
    public String password;
    public String confirmPassword;
    public String photo;
}
