package com.example.app2803.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2803.BaseActivity;
import com.example.app2803.R;
import com.example.app2803.account.LoginActivity;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.security.JwtSecurityService;
import com.example.app2803.user.dto.UserDTO;
import com.example.app2803.user.network.UserService;
import com.example.app2803.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    JwtSecurityService jwtService;

    @Override
    public void onResume() {
        super.onResume();
        request();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void request() {
        jwtService = (JwtSecurityService) HomeApplication.getInstance();
        String token = jwtService.getToken();
        if (token.isEmpty()) {
            Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_users);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                    LinearLayoutManager.VERTICAL, false));
            CommonUtils.showLoading(this);
            UserService.getInstance()
                    .jsonApi()
                    .users()
                    .enqueue(new Callback<List<UserDTO>>() {
                        @Override
                        public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                            RecyclerView.Adapter mAdapter;
                            mAdapter = new CardAdapter(response.body());
                            mRecyclerView.setAdapter(mAdapter);
                            CommonUtils.hideLoading();
                        }

                        @Override
                        public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                            String str = t.toString();
                            int a = 12;
                        }
                    });
        }
    }
}