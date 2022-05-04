package com.example.app2803.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.app2803.BaseActivity;
import com.example.app2803.MainActivity;
import com.example.app2803.R;
import com.example.app2803.account.dto.AccountResponseDTO;
import com.example.app2803.account.dto.SignUpDTO;
import com.example.app2803.account.network.AccountService;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.security.JwtSecurityService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {
    private TextInputLayout textInputFirstName;
    private TextInputLayout textInputSecondName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhone;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private TextInputEditText textEditFirstName;
    private TextInputEditText textEditSecondName;
    private TextInputEditText textEditEmail;
    private TextInputEditText textEditPhone;
    private TextInputEditText textEditPassword;
    private TextInputEditText textEditConfirmPassword;

    private ImageView previewImage;

    private static final Pattern EMAIL_REGEX = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{8,}$");

    private SignUpDTO dto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dto = new SignUpDTO();

        previewImage = findViewById(R.id.previewImage);

        textInputFirstName = findViewById(R.id.textInputFirstName);
        textInputSecondName = findViewById(R.id.textInputSecondName);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPhone = findViewById(R.id.textInputPhone);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        textEditFirstName = findViewById(R.id.textEditFirstName);
        textEditSecondName = findViewById(R.id.textEditSecondName);
        textEditEmail = findViewById(R.id.textEditEmail);
        textEditPhone = findViewById(R.id.textEditPhone);
        textEditPassword = findViewById(R.id.textEditPassword);
        textEditConfirmPassword = findViewById(R.id.textEditConfirmPassword);

        //First name
        textEditFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFirstNameValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Second name
        textEditSecondName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSecondNameValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Email
        textEditEmail.addTextChangedListener(new TextWatcher() {
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
        //Phone
        textEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPhoneValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Password
        textEditPassword.addTextChangedListener(new TextWatcher() {
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
        //Confirm Password
        textEditConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isConfirmPasswordValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onSignUpBtnClick(View view) {

        if (validate()) {
            AccountService.getInstance()
                    .jsonApi()
                    .signUp(dto)
                    .enqueue(new Callback<AccountResponseDTO>() {
                        @Override
                        public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
                            if (response.isSuccessful()) {
                                AccountResponseDTO data = response.body();
                                JwtSecurityService jwtService = (JwtSecurityService) HomeApplication.getInstance();
                                jwtService.saveJwtToken(data.getToken());
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
                            System.out.println("Sign up failed");
                        }
                    });
        }
    }

    boolean validate() {
        boolean isValid = true;
        if (!isFirstNameValid())
            isValid = false;
        if (!isSecondNameValid())
            isValid = false;
        if (!isEmailValid())
            isValid = false;
        if (!isPhoneValid())
            isValid = false;
        if (!isPasswordValid())
            isValid = false;
        if (!isConfirmPasswordValid())
            isValid = false;

        return isValid;
    }

    boolean isFirstNameValid() {
        String firstName = textEditFirstName.getText().toString();

        if (firstName.isEmpty()) {
            textInputFirstName.setError("First name is required");
        } else if (firstName.length() < 2) {
            textInputFirstName.setError("Must be at least 2 characters");
        } else {
            textInputFirstName.setError(null);
            dto.setFirstName(firstName);
            return true;
        }
        return false;
    }

    boolean isSecondNameValid() {
        String secondName = textEditSecondName.getText().toString();

        if (secondName.isEmpty()) {
            textInputSecondName.setError("Second name is required");
        } else if (secondName.length() < 2) {
            textInputSecondName.setError("Must be at least 2 characters");
        } else {
            textInputSecondName.setError(null);
            dto.setSecondName(secondName);
            return true;
        }
        return false;
    }

    boolean isEmailValid() {
        String email = textEditEmail.getText().toString();

        if (email.isEmpty()) {
            textInputEmail.setError("Email is required");
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            textInputEmail.setError("Invalid format of email");
        } else {
            textInputEmail.setError(null);
            dto.setEmail(email);
            return true;
        }
        return false;
    }

    boolean isPhoneValid() {
        String phone = textEditPhone.getText().toString();

        if (phone.isEmpty()) {
            textInputPhone.setError("Phone is required");
        } else if (!PHONE_REGEX.matcher(phone).matches()) {
            textInputPhone.setError("Invalid format of phone");
        } else {
            textInputPhone.setError(null);
            dto.setPhone(phone);
            return true;
        }
        return false;
    }

    boolean isPasswordValid() {
        String password = textEditPassword.getText().toString();

        if (password.isEmpty()) {
            textInputPassword.setError("Password is required");
        } else if (!PASSWORD_REGEX.matcher(password).matches()) {
            textInputPassword.setError("Invalid format of password");
        } else {
            textInputPassword.setError(null);
            dto.setPassword(password);
            return true;
        }
        return false;
    }

    boolean isConfirmPasswordValid() {
        String confirmPassword = textEditConfirmPassword.getText().toString();
        String password = textEditPassword.getText().toString();

        if (confirmPassword.isEmpty()) {
            textInputConfirmPassword.setError("Confirm password is required");
        } else if (!confirmPassword.equals(password)) {
            textInputConfirmPassword.setError("Password and Confirm password do not match!");
        } else {
            textInputConfirmPassword.setError(null);
            dto.setConfirmPassword(confirmPassword);
            return true;
        }
        return false;
    }

    public void onSelectImage(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropMenuCropButtonIcon(R.drawable.apply_image_icon)
                .setActivityMenuIconColor(R.color.black)
                .setActivityTitle("Crop")
                .setRequestedSize(400, 400)
                .start(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                previewImage.setImageURI(result.getUri());

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                String sImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                dto.setPhoto(sImage);
            }
        }
    }
}