package com.shopping.lifeplay.model;

/**
 * Created by Priyanka on 3/7/2018.
 */

public class ServiceHistory
{
    private String booking_id,payment_amount,booking_date,booking_day,service_status;
    public ServiceHistory(String booking_id,String payment_amount,String booking_date,String booking_day,String service_status)

    {
        this.booking_id=booking_id;
        this.payment_amount=payment_amount;
        this.booking_date=booking_date;
        this.booking_day=booking_day;
        this.service_status=service_status;
    }

    public String getService_status() {
        return service_status;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getBooking_day() {
        return booking_day;
    }

    public void setBooking_day(String booking_day) {
        this.booking_day = booking_day;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
}
