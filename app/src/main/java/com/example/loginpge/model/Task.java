package com.example.loginpge.model;

public class Task {
    private int id;
    private String task;
    private String date;
    private String time;

    public Task(int id, String task, String date, String time) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.time = time;
    }
    public Task(){}

    public Task(String task, String date, String time) {
        this.task = task;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
