package com.example.me_practice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.me_practice.DTO.PostDTO;
import com.example.me_practice.R;

import java.util.List;

public class PostADAPTER extends RecyclerView.Adapter <PostADAPTER.PostViewHolder> {
    List<PostDTO> mData;
    Context context;
    PostViewHolder.RecyclerViewClickListner recyclerViewClickListner;

    public PostADAPTER(List<PostDTO> mData, Context context, PostViewHolder.RecyclerViewClickListner recyclerViewClickListner) {
        this.mData = mData;
        this.context = context;
        this.recyclerViewClickListner = recyclerViewClickListner;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(context).inflate(R.layout.list_template_layout,parent,false);
        return new PostViewHolder(layout,context,mData,recyclerViewClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        String name = mData.get(position).getTv_name();
        String time = mData.get(position).getTv_time();
        String description = mData.get(position).getTv_description();
        int post = mData.get(position).getImage_post();
        int user = mData.get(position).getImage_user();

        holder.tv_name.setText(name);
        holder.tv_time.setText(time);
        holder.tv_description.setText(description);
        holder.img_post.setImageResource(post);
        holder.img_user.setImageResource(user);

        //set toast for single icon

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Your name is "+name ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_time,tv_description;
        ImageView img_user,img_post;
        RecyclerViewClickListner recyclerViewClickListner;

        public PostViewHolder(@NonNull View itemView, Context context, List<PostDTO> mData, RecyclerViewClickListner recyclerViewClickListner) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_description = itemView.findViewById(R.id.tv_description);
            img_user = itemView.findViewById(R.id.image_user);
            img_post = itemView.findViewById(R.id.image_post);
            this.recyclerViewClickListner = recyclerViewClickListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            recyclerViewClickListner.onClickListner(getAdapterPosition());
        }
        public interface RecyclerViewClickListner{
            void onClickListner(int position);
        }
    }
}