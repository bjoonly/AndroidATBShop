package com.example.app2803.account.network;

import com.example.app2803.account.dto.AccountResponseDTO;
import com.example.app2803.account.dto.LoginDTO;
import com.example.app2803.account.dto.SignUpDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("api/Account/register")
    Call<AccountResponseDTO> signUp(@Body SignUpDTO dto);

    @POST("api/Account/login")
    Call<AccountResponseDTO> login(@Body LoginDTO dto);
}
