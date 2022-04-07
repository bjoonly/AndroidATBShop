package com.example.app2803.account;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app2803.R;

public class SignUpActivity extends AppCompatActivity {
    // TextInputLayout textFieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //  textFieldEmail = findViewById(R.id.textFieldEmail);
    }

    public void onSignUpBtnClick(View view) {
        //  textFieldEmail.setError("Вкажіть пошту");
//        SignUpDTO dto = new SignUpDTO();
//        dto.setEmail("email@gmail.com");
//
//        AccountService.getInstance()
//                .jsonApi()
//                .signUp(dto)
//                .enqueue(new Callback<AccountResponseDTO>() {
//                    @Override
//                    public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
//                        AccountResponseDTO data = response.body();
////                        tvInfo.setText("response is good");
//                    }
//
//                    @Override
//                    public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
//                        String str = t.toString();
//                        int a = 12;
//                    }
//                });
    }
}