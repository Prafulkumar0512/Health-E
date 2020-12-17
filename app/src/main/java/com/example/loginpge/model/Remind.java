package com.example.loginpge.model;

public class Remind {

    int id;
    String title;
    String Amount;
    String remind_date;
    String due_date;
    String month1;

    public Remind(int id, String title, String amount, String remind_date, String due_date) {
        this.id = id;
        this.title = title;
        Amount = amount;
        this.remind_date = remind_date;
        this.due_date = due_date;
    }

    public Remind(String title, String amount, String remind_date, String due_date) {
        this.title = title;
        Amount = amount;
        this.remind_date = remind_date;
        this.due_date = due_date;
    }

    public Remind(String title, String amount, String remind_date, String due_date, String month1) {
        this.title = title;
        Amount = amount;
        this.remind_date = remind_date;
        this.due_date = due_date;
        this.month1 = month1;
    }

    public String getMonth1() {
        return month1;
    }

    public void setMonth1(String month) {
        this.month1 = month;
    }

    public Remind(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRemind_date() {
        return remind_date;
    }

    public void setRemind_date(String remind_date) {
        this.remind_date = remind_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
