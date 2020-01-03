package com.example.modabba;

public class ActiveSubcription {
    private String subcriptionid;
    private String plan_name;
    private String start_date;
    private String end_date;
    private String dabba;

    public  ActiveSubcription()
    {

    }

    public String getDabba() {
        return dabba;
    }

    public void setDabba(String dabba) {
        this.dabba = dabba;
    }

    public ActiveSubcription(String subcriptionid, String plan_name, String start_date, String end_date, String dabba) {
        this.subcriptionid = subcriptionid;
        this.plan_name = plan_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.dabba=dabba;
    }

    public String getSubcriptionid() {
        return subcriptionid;
    }

    public void setSubcriptionid(String subcriptionid) {
        this.subcriptionid = subcriptionid;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
