package com.example.me_practice.DTO;

import android.widget.ImageView;
import android.widget.TextView;

public class PostDTO {
    String tv_name,tv_time,tv_description;
    int image_user,image_post;

    public PostDTO(String tv_name, String tv_time, String tv_description, int image_user, int image_post) {
        this.tv_name = tv_name;
        this.tv_time = tv_time;
        this.tv_description = tv_description;
        this.image_user = image_user;
        this.image_post = image_post;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }

    public String getTv_description() {
        return tv_description;
    }

    public void setTv_description(String tv_description) {
        this.tv_description = tv_description;
    }

    public int getImage_user() {
        return image_user;
    }

    public void setImage_user(int image_user) {
        this.image_user = image_user;
    }

    public int getImage_post() {
        return image_post;
    }

    public void setImage_post(int image_post) {
        this.image_post = image_post;
    }
}
