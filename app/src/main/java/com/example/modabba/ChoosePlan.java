package com.example.modabba;

public class ChoosePlan {
    String plan_name;
    String plan_price;
    int meal;
    int combo;

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public ChoosePlan(){}
    public ChoosePlan(String plan_name, String plan_price,int meal,int combo) {
        this.plan_name = plan_name;
        this.plan_price = plan_price;
        this.meal=meal;
        this.combo=combo;
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
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
