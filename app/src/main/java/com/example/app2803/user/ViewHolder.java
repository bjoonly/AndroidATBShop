package com.example.app2803.user;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.app2803.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public NetworkImageView userPhoto;
    public TextView userEmail;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        userPhoto = itemView.findViewById(R.id.user_photo);
        userEmail = itemView.findViewById(R.id.user_email);
    }

    public View getView() {
        return view;
    }
}
