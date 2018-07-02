package com.shopping.lifeplay.model;

/**
 * Created by user on 12/20/2017.
 */

public class Product {
    private String product_discription;
    private String product_name;
    private int product_id;
    private String product_image;
    private String product_price;
    private String product_cut_price;
    private String product_disc;
    private String product_how_to_apply;
    private String product_uses;

    public Product(String product_discription, String product_name, int product_id, String product_image, String product_price, String product_cut_price, String product_disc, String product_how_to_apply, String product_uses) {
        this.product_discription = product_discription;
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_cut_price = product_cut_price;
        this.product_disc = product_disc;
        this.product_how_to_apply = product_how_to_apply;
        this.product_uses = product_uses;
    }

    public String getProduct_discription() {
        return product_discription;
    }

    public void setProduct_discription(String product_discription) {
        this.product_discription = product_discription;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_cut_price() {
        return product_cut_price;
    }

    public void setProduct_cut_price(String product_cut_price) {
        this.product_cut_price = product_cut_price;
    }

    public String getProduct_disc() {
        return product_disc;
    }

    public void setProduct_disc(String product_disc) {
        this.product_disc = product_disc;
    }

    public String getProduct_how_to_apply() {
        return product_how_to_apply;
    }

    public void setProduct_how_to_apply(String product_how_to_apply) {
        this.product_how_to_apply = product_how_to_apply;
    }

    public String getProduct_uses() {
        return product_uses;
    }

    public void setProduct_uses(String product_uses) {
        this.product_uses = product_uses;
    }
}
