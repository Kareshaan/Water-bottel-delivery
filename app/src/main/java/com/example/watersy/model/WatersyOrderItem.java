package com.example.watersy.model;

import java.io.Serializable;

public class WatersyOrderItem implements Serializable {
    public String id;
    public String name;
    public double itemPrice;
    public int qty;

    public WatersyOrderItem(String id, String name, double itemPrice, int qty) {
        this.id = id;
        this.name = name;
        this.itemPrice = itemPrice;
        this.qty = qty;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
