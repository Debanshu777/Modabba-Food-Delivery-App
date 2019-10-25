package com.example.modabba.Utils;

public class Feedback {

    private long rating;
    private String suggestion;

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Feedback(long rating, String suggestion) {
        this.rating = rating;
        this.suggestion = suggestion;
    }
}
