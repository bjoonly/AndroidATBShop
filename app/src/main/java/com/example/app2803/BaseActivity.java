package com.example.app2803;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app2803.account.LoginActivity;
import com.example.app2803.account.SignUpActivity;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.security.JwtSecurityService;
import com.example.app2803.user.UserActivity;

public class BaseActivity extends AppCompatActivity {
    private static boolean isAuth;

    public static boolean getIsAuth() {
        return isAuth;
    }

    public static void setIsAuth(boolean isAuth) {
        BaseActivity.isAuth = isAuth;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        JwtSecurityService jwtService = HomeApplication.getInstance();
        String token = jwtService.getToken();

        MenuItem loginItem = menu.findItem(R.id.m_login);
        MenuItem signUpItem = menu.findItem(R.id.m_sign_up);
        MenuItem usersItem = menu.findItem(R.id.m_users);
        MenuItem logoutItem = menu.findItem(R.id.m_logout);
        if (!token.isEmpty()) {
            loginItem.setVisible(false);
            signUpItem.setVisible(false);
            usersItem.setVisible(true);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            signUpItem.setVisible(true);
            usersItem.setVisible(false);
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.m_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_sign_up:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_users:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                return true;
            case R.id.m_logout:
                JwtSecurityService jwtService = HomeApplication.getInstance();
                String token = jwtService.getToken();
                if (token != null && !token.isEmpty()) {
                    jwtService.deleteToken();
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
