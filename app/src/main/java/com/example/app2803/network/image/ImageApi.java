package com.example.app2803.network.image;

import com.example.app2803.network.image.dto.ImageRequestDto;
import com.example.app2803.network.image.dto.ImageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageApi {
    @POST("/api/Image/upload")
    Call<ImageResponse> UploadImage(@Body ImageRequestDto image);
}
