package com.shopping.lifeplay.model;

/**
 * Created by Priyanka on 3/7/2018.
 */

public class DealsHistory
{
    private String offer_expiry,offer_company,offer_description,offer_status,offer_price,company_image;
    public DealsHistory(String offer_expiry,String offer_company,String offer_description,String offer_status,String offer_price,String company_image)

    {
        this.offer_expiry=offer_expiry;
        this.offer_company=offer_company;
        this.offer_description=offer_description;
        this.offer_status=offer_status;
        this.offer_price=offer_price;
        this.company_image=company_image;

    }

    public String getOffer_expiry() {
        return offer_expiry;
    }

    public void setOffer_expiry(String offer_expiry) {
        this.offer_expiry = offer_expiry;
    }

    public String getOffer_company() {
        return offer_company;
    }

    public void setOffer_company(String offer_company) {
        this.offer_company = offer_company;
    }

    public String getOffer_description() {
        return offer_description;
    }

    public void setOffer_description(String offer_description) {
        this.offer_description = offer_description;
    }

    public String getOffer_status() {
        return offer_status;
    }

    public void setOffer_status(String offer_status) {
        this.offer_status = offer_status;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getCompany_image() {
        return company_image;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }
}
