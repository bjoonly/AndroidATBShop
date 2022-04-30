package com.example.app2803.user.usercard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app2803.R;
import com.example.app2803.constants.Urls;
import com.example.app2803.network.ImageRequester;
import com.example.app2803.user.dto.UserDTO;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<UserDTO> users;
    private ImageRequester imageRequester;
    private final OnItemClickListener listener;
    private final OnItemClickListener editUser;

    public CardAdapter(List<UserDTO> items, OnItemClickListener listener,
                       OnItemClickListener editUser) {
        super();
        users = items;
        this.listener = listener;
        this.editUser = editUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (users != null && position < users.size()) {
            UserDTO user = users.get(position);
            viewHolder.userEmail.setText(user.getEmail());
            imageRequester = ImageRequester.getInstance();
            String urlImg = Urls.BASE + user.getPhoto();

            imageRequester.setImageFromUrl(viewHolder.userPhoto, urlImg);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(user);
                }
            });

            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUser.onItemClick(user);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}