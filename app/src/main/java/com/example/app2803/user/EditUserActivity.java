package com.example.app2803.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.app2803.BaseActivity;
import com.example.app2803.R;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.constants.Urls;
import com.example.app2803.network.ImageRequester;
import com.example.app2803.user.dto.EditUserDTO;
import com.example.app2803.user.dto.UserDTO;
import com.example.app2803.user.network.UserService;
import com.example.app2803.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends BaseActivity {

    private EditUserDTO editUserDTO;
    private int id;

    private TextInputLayout userEditTextInputFirstName;
    private TextInputLayout userEditTextInputSecondName;
    private TextInputLayout userEditTextInputEmail;
    private TextInputLayout userEditTextInputPhone;


    private TextInputEditText userEditTextEditFirstName;
    private TextInputEditText userEditTextEditSecondName;
    private TextInputEditText userEditTextEditEmail;
    private TextInputEditText userEditTextEditPhone;

    private NetworkImageView userEditPhoto;

    private ImageRequester imageRequester;

    int SELECT_PICTURE = 200;


    private static final Pattern EMAIL_REGEX = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^((\\\\+[1-9]{1,4}[ \\\\-]*)|(\\\\([0-9]{2,3}\\\\)[ \\\\-]*)|([0-9]{2,4})[ \\\\-]*)*?[0-9]{3,4}?[ \\\\-]*[0-9]{3,4}?$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editUserDTO = new EditUserDTO();

        userEditTextInputFirstName = findViewById(R.id.userEditTextInputFirstName);
        userEditTextInputSecondName = findViewById(R.id.userEditTextInputSecondName);
        userEditTextInputEmail = findViewById(R.id.userEditTextInputEmail);
        userEditTextInputPhone = findViewById(R.id.userEditTextInputPhone);

        userEditTextEditFirstName = findViewById(R.id.userEditTextEditFirstName);
        userEditTextEditSecondName = findViewById(R.id.userEditTextEditSecondName);
        userEditTextEditEmail = findViewById(R.id.userEditTextEditEmail);
        userEditTextEditPhone = findViewById(R.id.userEditTextEditPhone);

        userEditPhoto = findViewById(R.id.userEditPhoto);

        //First name
        userEditTextEditFirstName.addTextChangedListener(new TextWatcher() {
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
        userEditTextEditSecondName.addTextChangedListener(new TextWatcher() {
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
        userEditTextEditEmail.addTextChangedListener(new TextWatcher() {
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
        userEditTextEditPhone.addTextChangedListener(new TextWatcher() {
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

        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey("id")) {
            Toast.makeText(HomeApplication.getAppContext(), "Invalid user id", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            id = b.getInt("id");

            CommonUtils.showLoading(this);

            UserService.getInstance()
                    .jsonApi()
                    .getUser(id)
                    .enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            UserDTO user = response.body();

                            editUserDTO.setEmail(user.getEmail());
                            editUserDTO.setFirstName(user.getFirstName());
                            editUserDTO.setSecondName(user.getSecondName());
                            editUserDTO.setPhone(user.getPhone());
                            editUserDTO.setPhoto(user.getPhoto());


                            userEditTextEditFirstName.setText(user.getFirstName());
                            userEditTextEditSecondName.setText(user.getSecondName());
                            userEditTextEditEmail.setText(user.getEmail());
                            userEditTextEditPhone.setText(user.getPhone());

                            imageRequester = ImageRequester.getInstance();
                            String urlImg = Urls.BASE + user.getPhoto();

                            imageRequester.setImageFromUrl(userEditPhoto, urlImg);


                            CommonUtils.hideLoading();
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            String str = t.toString();
                            int a = 12;
                        }
                    });
        }
    }

    public void onEditUserBtnClick(View view) {

        if (validate()) {
            UserService.getInstance()
                    .jsonApi()
                    .editUser(id, editUserDTO)
                    .enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            Intent intent = new Intent(EditUserActivity.this, UsersActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable t) {
                            String str = t.toString();
                            int a = 12;
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

        return isValid;
    }

    boolean isFirstNameValid() {
        String firstName = userEditTextEditFirstName.getText().toString();

        if (firstName.isEmpty()) {
            userEditTextInputFirstName.setError("First name is required");
        } else if (firstName.length() < 2) {
            userEditTextInputFirstName.setError("Must be at least 2 characters");
        } else {
            userEditTextInputFirstName.setError(null);
            editUserDTO.setFirstName(firstName);
            return true;
        }
        return false;
    }

    boolean isSecondNameValid() {
        String secondName = userEditTextEditSecondName.getText().toString();

        if (secondName.isEmpty()) {
            userEditTextInputSecondName.setError("Second name is required");
        } else if (secondName.length() < 2) {
            userEditTextInputSecondName.setError("Must be at least 2 characters");
        } else {
            userEditTextInputSecondName.setError(null);
            editUserDTO.setSecondName(secondName);
            return true;
        }
        return false;
    }

    boolean isEmailValid() {
        String email = userEditTextEditEmail.getText().toString();

        if (email.isEmpty()) {
            userEditTextInputEmail.setError("Email is required");
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            userEditTextInputEmail.setError("Invalid format of email");
        } else {
            userEditTextInputEmail.setError(null);
            editUserDTO.setEmail(email);
            return true;
        }
        return false;
    }

    boolean isPhoneValid() {
        String phone = userEditTextEditPhone.getText().toString();

        if (phone.isEmpty()) {
            userEditTextInputPhone.setError("Phone is required");
        } else if (!PHONE_REGEX.matcher(phone).matches()) {
            userEditTextInputPhone.setError("Invalid format of phone");
        } else {
            userEditTextInputPhone.setError(null);
            editUserDTO.setPhone(phone);
            return true;
        }
        return false;
    }

    public void onEditUserOnSelectPhotoBtnClick(View view) {
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

                    userEditPhoto.setImageURI(uri);

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

                    editUserDTO.setPhoto(sImage);
                }
            }
        }
    }


}