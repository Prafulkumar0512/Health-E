package com.example.loginpge.model;

public class Waterremainder {
    private int id;
    private String time;
    private String text;
    private String date;

    public int getWaterml() {
        return waterml;
    }

    public void setWaterml(int waterml) {
        this.waterml = waterml;
    }

    private int waterml;

    public Waterremainder(int id, String time, String text, String date,int waterml) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.date = date;
        this.waterml=waterml;
    }

    public Waterremainder(String time, String text, String date,int waterml) {
        this.time = time;
        this.text = text;
        this.date = date;
        this.waterml=waterml;
    }
    public Waterremainder(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
