package com.example.app2803.network;

import com.example.app2803.constants.Urls;
import com.example.app2803.user.network.UserApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private static UserService instance;
    private Retrofit retrofit;

    private UserService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public UserApi jsonApi() {
        return retrofit.create(UserApi.class);
    }
}
