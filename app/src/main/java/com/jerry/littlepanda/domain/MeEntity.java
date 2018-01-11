package com.jerry.littlepanda.domain;

/**
 * Created by jerry.hu on 20/10/17.
 */

public class MeEntity {

    public String title;
    public int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public MeEntity(String title, int image) {
        this.title = title;
        this.image = image;
    }
}
