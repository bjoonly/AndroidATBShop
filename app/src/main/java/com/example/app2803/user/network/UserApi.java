package com.example.app2803.user.network;

import com.example.app2803.user.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("/api/Account/users")
    public Call<List<UserDTO>> users();
}
