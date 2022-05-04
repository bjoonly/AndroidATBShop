package com.example.app2803.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.app2803.BaseActivity;
import com.example.app2803.MainActivity;
import com.example.app2803.R;
import com.example.app2803.account.dto.AccountResponseDTO;
import com.example.app2803.account.dto.LoginDTO;
import com.example.app2803.account.network.AccountService;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.security.JwtSecurityService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    LoginDTO dto;

    TextInputLayout loginTextInputEmail;
    TextInputLayout loginTextInputPassword;

    TextInputEditText loginTextEditEmail;
    TextInputEditText loginTextEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dto = new LoginDTO();

        loginTextInputEmail = findViewById(R.id.loginTextInputEmail);
        loginTextInputPassword = findViewById(R.id.loginTextInputPassword);

        loginTextEditEmail = findViewById(R.id.loginTextEditEmail);
        loginTextEditPassword = findViewById(R.id.loginTextEditPassword);

        //Email
        loginTextEditEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Password
        loginTextEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onLoginBtnClick(View view) {
        if (validate()) {
            AccountService.getInstance()
                    .jsonApi()
                    .login(dto)
                    .enqueue(new Callback<AccountResponseDTO>() {
                        @Override
                        public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
                            if (response.isSuccessful()) {
                                AccountResponseDTO data = response.body();

                                JwtSecurityService jwtService = (JwtSecurityService) HomeApplication.getInstance();
                                jwtService.saveJwtToken(data.getToken());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
                            String str = t.toString();
                            int a = 12;
                        }
                    });
        }
    }

    boolean validate() {
        boolean isValid = true;
        if (!isEmailValid())
            isValid = false;
        if (!isPasswordValid())
            isValid = false;

        return isValid;
    }

    boolean isEmailValid() {
        String email = loginTextEditEmail.getText().toString();

        if (email.isEmpty()) {
            loginTextInputEmail.setError("Email is required");
        } else {
            loginTextInputEmail.setError(null);
            dto.setEmail(email);
            return true;
        }
        return false;
    }

    boolean isPasswordValid() {
        String password = loginTextEditPassword.getText().toString();

        if (password.isEmpty()) {
            loginTextInputPassword.setError("Password is required");
        } else {
            loginTextInputPassword.setError(null);
            dto.setPassword(password);
            return true;
        }
        return false;
    }
}