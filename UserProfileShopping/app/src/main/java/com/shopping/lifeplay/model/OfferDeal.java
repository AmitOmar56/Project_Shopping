package com.shopping.lifeplay.model;

/**
 * Created by user on 1/11/2018.
 */

public class OfferDeal {
    private String offer_deal_discription;
    private String offer_deal_name;
    private String offer_deal_image;
    private String offer_deal_price;
    private int offer_deal_id;
    private String offer_deal_cutPrice;
    private int merchant_id;

    public OfferDeal(String offer_deal_discription, String offer_deal_name, int merchant_id, int offer_deal_id, String offer_deal_image, String offer_deal_price, String offer_deal_cutPrice) {
        this.offer_deal_discription = offer_deal_discription;
        this.offer_deal_name = offer_deal_name;
        this.offer_deal_id = offer_deal_id;
        this.offer_deal_image = offer_deal_image;
        this.offer_deal_price = offer_deal_price;
        this.offer_deal_cutPrice = offer_deal_cutPrice;
        this.merchant_id = merchant_id;
    }

    public String getOffer_deal_discription() {
        return offer_deal_discription;
    }

    public void setOffer_deal_discription(String offer_deal_discription) {
        this.offer_deal_discription = offer_deal_discription;
    }

    public String getOffer_deal_name() {
        return offer_deal_name;
    }

    public void setOffer_deal_name(String offer_deal_name) {
        this.offer_deal_name = offer_deal_name;
    }

    public String getOffer_deal_image() {
        return offer_deal_image;
    }

    public void setOffer_deal_image(String offer_deal_image) {
        this.offer_deal_image = offer_deal_image;
    }

    public String getOffer_deal_price() {
        return offer_deal_price;
    }

    public void setOffer_deal_price(String offer_deal_price) {
        this.offer_deal_price = offer_deal_price;
    }

    public int getOffer_deal_id() {
        return offer_deal_id;
    }

    public void setOffer_deal_id(int offer_deal_id) {
        this.offer_deal_id = offer_deal_id;
    }

    public String getOffer_deal_cutPrice() {
        return offer_deal_cutPrice;
    }

    public void setOffer_deal_cutPrice(String offer_deal_cutPrice) {
        this.offer_deal_cutPrice = offer_deal_cutPrice;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}