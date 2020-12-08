package com.example.mytest.object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PayloadItem {

    private String id;
    private String imageUrl;
    private boolean isClose;
    private String closeLabel;
    private String productName;
    private String productDesc;
    private double star;
    private String distance;
    private String promoDesc;
    private int outletAround;

    public PayloadItem() {
    }

    public static PayloadItem getInstance(PayloadItem responseJson){
        PayloadItem payloadItem = new PayloadItem();
        payloadItem.id = responseJson.getId();
        payloadItem.imageUrl = responseJson.getImageUrl();
        payloadItem.isClose = responseJson.isClose();
        payloadItem.closeLabel = responseJson.getCloseLabel();
        payloadItem.productName = responseJson.getProductName();
        payloadItem.productDesc = responseJson.getProductDesc();
        payloadItem.star = responseJson.getStar();
        payloadItem.distance = responseJson.getDistance();
        payloadItem.promoDesc = responseJson.getPromoDesc();
        payloadItem.outletAround = responseJson.getOutletAround();
        return payloadItem;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isClose() {
        return isClose;
    }

    public String getCloseLabel() {
        return closeLabel;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public double getStar() {
        return star;
    }

    public String getDistance() {
        return distance;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public int getOutletAround() {
        return outletAround;
    }
}
