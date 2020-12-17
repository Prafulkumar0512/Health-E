package com.example.loginpge.model;

public class Tablelist {

    String id;
    String Month;
    String billtitle;
    String Amount;
    String date;

    public Tablelist(String id, String month, String billtitle, String amount, String date) {
        this.id = id;
        Month = month;
        this.billtitle = billtitle;
        Amount = amount;
        this.date = date;
    }

    public Tablelist(String month, String billtitle, String amount, String date) {
        this.Month = month;
        this.billtitle = billtitle;
        this.Amount = amount;
        this.date = date;
    }
    public Tablelist(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getBilltitle() {
        return billtitle;
    }

    public void setBilltitle(String billtitle) {
        this.billtitle = billtitle;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
