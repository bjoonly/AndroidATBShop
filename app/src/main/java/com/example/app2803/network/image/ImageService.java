package com.example.app2803.network.image;

import com.example.app2803.constants.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageService {
    private static ImageService instance;
    private Retrofit retrofit;

    private ImageService() {
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

    public static ImageService getInstance() {
        if (instance == null)
            instance = new ImageService();
        return instance;
    }

    public ImageApi jsonApi() {
        return retrofit.create(ImageApi.class);
    }

}
