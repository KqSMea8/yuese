package com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean;

import java.io.Serializable;

/**
 * Created by admin on 2018/12/26.
 */

public class MineBean implements Serializable {

    int image;
    String name;

    public MineBean() {

    }

    public MineBean(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
