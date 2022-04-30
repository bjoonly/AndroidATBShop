package com.example.app2803.user.usercard;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.app2803.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public NetworkImageView userPhoto;
    public TextView userEmail;
    public Button btnEdit;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        userPhoto = itemView.findViewById(R.id.user_photo);
        userEmail = itemView.findViewById(R.id.user_email);
        btnEdit = itemView.findViewById(R.id.btnEdit);
    }

    public View getView() {
        return view;
    }
}
