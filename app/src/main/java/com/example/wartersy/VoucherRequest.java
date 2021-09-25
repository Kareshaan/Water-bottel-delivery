package com.example.wartersy;

import java.io.Serializable;

public class VoucherRequest implements Serializable {
    private String platinumCount, goldCount, silverCount, bronzeCount;

    public VoucherRequest() {
        //empty constructor for Firebase data base
    }

    public VoucherRequest(String platinumCount, String goldCount, String silverCount, String bronzeCount) {
        this.platinumCount = platinumCount;
        this.goldCount = goldCount;
        this.silverCount = silverCount;
        this.bronzeCount = bronzeCount;
    }

    public String getPlatinumCount() {
        return platinumCount;
    }

    public void setPlatinumCount(String platinumCount) {
        this.platinumCount = platinumCount;
    }

    public String getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(String goldCount) {
        this.goldCount = goldCount;
    }

    public String getSilverCount() {
        return silverCount;
    }

    public void setSilverCount(String silverCount) {
        this.silverCount = silverCount;
    }

    public String getBronzeCount() {
        return bronzeCount;
    }

    public void setBronzeCount(String bronzeCount) {
        this.bronzeCount = bronzeCount;
    }
}
