package com.example.app2803.security;

public interface JwtSecurityService {
    void saveJwtToken(String token);

    String getToken();

    void deleteToken();
}
