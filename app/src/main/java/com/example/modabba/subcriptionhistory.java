package com.example.modabba;

public class subcriptionhistory {
    String subcriptionid;
    String plan;
    String time;
    String AddorMinus;
    String amount;


    public subcriptionhistory(String subcriptionid, String plan, String time, String addorMinus, String amount) {
        this.subcriptionid = subcriptionid;
        this.plan = plan;
        this.time = time;
        AddorMinus = addorMinus;
        this.amount = amount;
    }

    public String getSubcriptionid() {
        return subcriptionid;
    }

    public void setSubcriptionid(String subcriptionid) {
        this.subcriptionid = subcriptionid;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddorMinus() {
        return AddorMinus;
    }

    public void setAddorMinus(String addorMinus) {
        AddorMinus = addorMinus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
