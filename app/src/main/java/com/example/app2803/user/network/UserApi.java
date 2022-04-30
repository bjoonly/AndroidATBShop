package com.example.app2803.user.network;

import com.example.app2803.user.dto.EditUserDTO;
import com.example.app2803.user.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/Account/users")
    public Call<List<UserDTO>> users();

    @GET("/api/Account/get-user/{id}")
    public Call<UserDTO> getUser(@Path("id") int id);

    @PUT("/api/Account/edit-user/{id}")
    public Call<UserDTO> editUser(@Path("id") int id, @Body EditUserDTO user);
}
