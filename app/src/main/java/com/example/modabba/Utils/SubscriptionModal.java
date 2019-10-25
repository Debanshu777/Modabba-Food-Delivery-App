package com.example.modabba.Utils;

public class SubscriptionModal {

    String activationDate;
    String expiryDate;
    String purchaseDate;
    long foodCategory; //veg/NonVeg
    long planDays; //7/14/30
    long mealCategory ;
    long status ;

    public String getActivationDate() {
        return activationDate;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(long foodCategory) {
        this.foodCategory = foodCategory;
    }

    public long getPlanDays() {
        return planDays;
    }

    public void setPlanDays(long planDays) {
        this.planDays = planDays;
    }

    public long getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(long mealCategory) {
        this.mealCategory = mealCategory;
    }

    public SubscriptionModal(String activationDate, String expiryDate, String purchaseDate, long foodCategory, long planDays, long mealCategory,long status) {
        this.activationDate = activationDate;
        this.expiryDate = expiryDate;
        this.purchaseDate = purchaseDate;
        this.foodCategory = foodCategory;
        this.planDays = planDays;
        this.mealCategory = mealCategory;
        this.status =status;
    }
}
