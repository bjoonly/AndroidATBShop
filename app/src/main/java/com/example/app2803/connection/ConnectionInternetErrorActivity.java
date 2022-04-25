package com.example.app2803.connection;

import android.os.Bundle;
import android.view.View;

import com.example.app2803.BaseActivity;
import com.example.app2803.R;
import com.example.app2803.utils.NetworkUtils;

public class ConnectionInternetErrorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_internet_error);
    }

    public void onRetryBtnClick(View view) {
        if (NetworkUtils.isOnline(this))
            onBackPressed();
    }
}