package com.example.modabba;

public class Model {

    private int image;
    private String title;
    private String desc;
    private int meal;

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    private int combo;

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public Model(int image, String title, String desc, int meal,int combo) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.meal = meal;
        this.combo=combo;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
