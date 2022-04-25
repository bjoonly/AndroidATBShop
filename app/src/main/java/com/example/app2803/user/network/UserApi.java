package com.example.app2803.user.network;

import com.example.app2803.user.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/Account/users")
    public Call<List<UserDTO>> users();

    @GET("/api/Account/get-user/{id}")
    public Call<List<UserDTO>> getUsers(@Path("id") int id);
}
