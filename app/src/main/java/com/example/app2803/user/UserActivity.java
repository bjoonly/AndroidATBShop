package com.example.app2803.user;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.app2803.BaseActivity;
import com.example.app2803.R;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.constants.Urls;
import com.example.app2803.network.ImageRequester;
import com.example.app2803.user.dto.UserDTO;
import com.example.app2803.user.network.UserService;
import com.example.app2803.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends BaseActivity {

    private TextInputEditText textEditUserInfoFirstName;
    private TextInputEditText textEditUserInfoSecondName;
    private TextInputEditText textEditUserInfoEmail;
    private TextInputEditText textEditUserInfoPhone;

    private NetworkImageView userInfoPhoto;
    private ImageRequester imageRequester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textEditUserInfoFirstName = findViewById(R.id.textEditUserInfoFirstName);
        textEditUserInfoSecondName = findViewById(R.id.textEditUserInfoSecondName);
        textEditUserInfoEmail = findViewById(R.id.textEditUserInfoEmail);
        textEditUserInfoPhone = findViewById(R.id.textEditUserInfoPhone);
        userInfoPhoto = findViewById(R.id.userInfoPhoto);


    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey("id")) {
            Toast.makeText(HomeApplication.getAppContext(), "Invalid user id", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            int id = b.getInt("id");

            CommonUtils.showLoading(this);
            UserService.getInstance()
                    .jsonApi()
                    .getUser(id)
                    .enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            UserDTO user = response.body();
                            textEditUserInfoFirstName.setText(user.getFirstName());
                            textEditUserInfoSecondName.setText(user.getSecondName());
                            textEditUserInfoEmail.setText(user.getEmail());
                            textEditUserInfoPhone.setText(user.getPhone());

                            imageRequester = ImageRequester.getInstance();
                            String urlImg = Urls.BASE + user.getPhoto();

                            imageRequester.setImageFromUrl(userInfoPhoto, urlImg);

                            CommonUtils.hideLoading();
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            System.out.println("Error");
                        }
                    });
        }
    }

}
