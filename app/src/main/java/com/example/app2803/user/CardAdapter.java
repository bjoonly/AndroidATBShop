package com.example.app2803.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.app2803.R;
import com.example.app2803.application.HomeApplication;
import com.example.app2803.constants.Urls;
import com.example.app2803.network.ImageRequester;
import com.example.app2803.user.dto.UserDTO;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<UserDTO> mItems;
    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    public CardAdapter(List<UserDTO> items) {
        super();
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (mItems != null && position < mItems.size()) {
            UserDTO user = mItems.get(position);
            viewHolder.userEmail.setText(user.getEmail());
            String url = Urls.BASE + user.getPhoto();
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(600, 300))
                    .into(viewHolder.userPhoto);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}