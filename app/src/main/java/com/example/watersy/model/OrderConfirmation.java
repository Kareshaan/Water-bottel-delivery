package com.example.watersy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class OrderConfirmation implements Serializable {
    public String id = String.valueOf(UUID.randomUUID());
    public String user;
    public ArrayList<WatersyOrderItem> items = new ArrayList<WatersyOrderItem>();
    public double totalPrice = 0;
    public String date;

    public OrderConfirmation() {
    }

    public OrderConfirmation(String user) {
        this.user = user;
    }

    public void addItem(WatersyOrderItem item){
        this.items.add(item);
        this.totalPrice+= (item.itemPrice * item.qty);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WatersyOrderItem getItem(String id){
        for (WatersyOrderItem item : this.items){
            if(item.id.equals(id)){
                return item;
            }
        }
        return null;
    }

    public boolean isItemAvailableInOrder(String id){
        for (WatersyOrderItem item : this.items){
            if(item.id.equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "OrderConfirmation{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +

                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                '}';
    }

    public boolean isValid(){
        return this.items.size() > 0;
    }
}

