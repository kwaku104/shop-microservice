package com.shop.notificationservice;


// not sharing this class with order services because we want services to be independent
public class OrderPlacedEvent {

    private String orderNumber;

    public OrderPlacedEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderPlacedEvent(){

    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
