package com.example.modabba;


public class FexiblePlanData {
    int imgid;
    String name;
    String descp;
    public FexiblePlanData()
    {
    }
    public FexiblePlanData(int imgid, String name, String descp) {
        this.imgid = imgid;
        this.name = name;
        this.descp = descp;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
