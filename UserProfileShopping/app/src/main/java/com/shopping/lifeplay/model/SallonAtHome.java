package com.shopping.lifeplay.model;

/**
 * Created by user on 12/26/2017.
 */

public class SallonAtHome {
    private String s_image;
    private String s_name;
    private int s_id;


    public SallonAtHome(String s_image, String s_name, int s_id) {
        this.s_image = s_image;
        this.s_name = s_name;
        this.s_id = s_id;
    }

    public String getS_image() {
        return s_image;
    }

    public void setS_image(String s_image) {
        this.s_image = s_image;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }
}
