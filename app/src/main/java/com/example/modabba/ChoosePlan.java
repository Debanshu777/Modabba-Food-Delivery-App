package com.example.modabba;

public class ChoosePlan {
    String plan_name;
    String plan_price;

    public ChoosePlan(){}
    public ChoosePlan(String plan_name, String plan_price) {
        this.plan_name = plan_name;
        this.plan_price = plan_price;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_price() {
        return plan_price;
    }

    public void setPlan_price(String plan_price) {
        this.plan_price = plan_price;
    }
}
