package com.shopping.lifeplay.model;

/**
 * Created by user on 12/27/2017.
 */

public class SalonCart {
    private String cart_data_name;
    private String cart_data_price;
    private int cart_data_id;


    public SalonCart(String cart_data_name, String cart_data_price, int cart_data_id) {
        this.cart_data_name = cart_data_name;
        this.cart_data_price = cart_data_price;
        this.cart_data_id = cart_data_id;
    }

    public String getCart_data_name() {
        return cart_data_name;
    }

    public void setCart_data_name(String cart_data_name) {
        this.cart_data_name = cart_data_name;
    }

    public String getCart_data_price() {
        return cart_data_price;
    }

    public void setCart_data_price(String cart_data_price) {
        this.cart_data_price = cart_data_price;
    }

    public int getCart_data_id() {
        return cart_data_id;
    }

    public void setCart_data_id(int cart_data_id) {
        this.cart_data_id = cart_data_id;
    }
}
