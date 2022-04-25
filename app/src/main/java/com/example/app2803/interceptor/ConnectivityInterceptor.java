package com.example.app2803.interceptor;

import android.content.Context;

import com.example.app2803.application.HomeApplication;
import com.example.app2803.connection.ConnectionInternetError;
import com.example.app2803.utils.CommonUtils;
import com.example.app2803.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ConnectivityInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context = HomeApplication.getAppContext();
        Request originalRequest = chain.request();

        if (!NetworkUtils.isOnline(context)) {
            HomeApplication beginApplication = (HomeApplication) context;
            CommonUtils.hideLoading();
            ((ConnectionInternetError) beginApplication.getCurrentActivity()).navigateErrorPage();

        }
        Request newRequest = originalRequest.newBuilder()
                .build();
        return chain.proceed(newRequest);
    }
}
