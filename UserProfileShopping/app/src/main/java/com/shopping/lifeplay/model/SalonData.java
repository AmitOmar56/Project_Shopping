package com.shopping.lifeplay.model;

/**
 * Created by user on 12/26/2017.
 */

public class SalonData {
    private String salon_data_name;
    private String salon_data_price;
    private String salon_data_include;
    private String salon_data_cut_price;
    private String salon_data_time;
    private int salon_data_id;


    public SalonData(String salon_data_name, String salon_data_include, String salon_data_price, String salon_data_cut_price, String salon_data_time, int salon_data_id) {
        this.salon_data_name = salon_data_name;
        this.salon_data_price = salon_data_price;
        this.salon_data_time = salon_data_time;
        this.salon_data_id = salon_data_id;
        this.salon_data_cut_price = salon_data_cut_price;
        this.salon_data_include = salon_data_include;
    }

    public String getSalon_data_name() {
        return salon_data_name;
    }

    public void setSalon_data_name(String salon_data_name) {
        this.salon_data_name = salon_data_name;
    }

    public String getSalon_data_price() {
        return salon_data_price;
    }

    public void setSalon_data_price(String salon_data_price) {
        this.salon_data_price = salon_data_price;
    }

    public String getSalon_data_include() {
        return salon_data_include;
    }

    public void setSalon_data_include(String salon_data_include) {
        this.salon_data_include = salon_data_include;
    }

    public String getSalon_data_cut_price() {
        return salon_data_cut_price;
    }

    public void setSalon_data_cut_price(String salon_data_cut_price) {
        this.salon_data_cut_price = salon_data_cut_price;
    }

    public String getSalon_data_time() {
        return salon_data_time;
    }

    public void setSalon_data_time(String salon_data_time) {
        this.salon_data_time = salon_data_time;
    }

    public int getSalon_data_id() {
        return salon_data_id;
    }

    public void setSalon_data_id(int salon_data_id) {
        this.salon_data_id = salon_data_id;
    }
}
