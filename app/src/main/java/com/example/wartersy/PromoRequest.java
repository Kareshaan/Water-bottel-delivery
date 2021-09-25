package com.example.wartersy;

public class PromoRequest {
    private String promoName, bottleSize, quantity, price;

    public PromoRequest() {}

    public PromoRequest(String promoName, String bottleSize, String quantity, String price) {
        this.promoName = promoName;
        this.bottleSize = bottleSize;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(String bottleSize) {
        this.bottleSize = bottleSize;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
