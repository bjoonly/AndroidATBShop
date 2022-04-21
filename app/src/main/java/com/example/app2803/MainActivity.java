package com.example.app2803;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.app2803.constants.Urls;
import com.example.app2803.network.ImageRequester;
import com.example.app2803.network.image.ImageService;
import com.example.app2803.network.image.dto.ImageRequestDto;
import com.example.app2803.network.image.dto.ImageResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private ImageRequester imageRequester;
    private NetworkImageView myImage;
    int SELECT_PICTURE = 200;
    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRequester = ImageRequester.getInstance();
        myImage = findViewById(R.id.myimg);
        String urlImg = Urls.BASE + "/images/1.jpg";
        imageRequester.setImageFromUrl(myImage, urlImg);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
    }

    public void onSelectImage(View view) {
        imageChooser();
    }

    void imageChooser() {


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                Uri uri = data.getData();
                if (null != uri) {

                    IVPreviewImage.setImageURI(uri);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    String sImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                    ImageRequestDto imageRequestDto = new ImageRequestDto();
                    imageRequestDto.setBase64(sImage);

                    ImageService.getInstance()
                            .jsonApi()
                            .UploadImage(imageRequestDto)
                            .enqueue(new Callback<ImageResponse>() {
                                @Override
                                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                                    ImageResponse data = response.body();
                                    String urlImg = Urls.BASE + "/images/" + data.getFileName();
                                    imageRequester.setImageFromUrl(myImage, urlImg);
                                }

                                @Override
                                public void onFailure(Call<ImageResponse> call, Throwable t) {

                                }
                            });

                }
            }
        }
    }
}