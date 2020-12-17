package com.example.loginpge.model;

public class Data {
    private int id;
    private String Firstname;
    private String lastname;
    private String Email;
    private String date;
    private int age;
    private String gender;
    private String country;
    private String userid;
    private String pascode;
    private String Anydisease1;
    private String Anydisease2;
    private String Anydisease3;

    public String getAnydisease1() {
        return Anydisease1;
    }

    public void setAnydisease1(String anydisease1) {
        Anydisease1 = anydisease1;
    }

    public String getAnydisease2() {
        return Anydisease2;
    }

    public void setAnydisease2(String anydisease2) {
        Anydisease2 = anydisease2;
    }

    public String getAnydisease3() {
        return Anydisease3;
    }

    public void setAnydisease3(String anydisease3) {
        Anydisease3 = anydisease3;
    }


    public Data(){}

    public Data(int id,String firstname, String lastname, String email, String date, int age,  String country, String anydisease1, String anydisease2, String anydisease3) {
        this.id=id;
        Firstname = firstname;
        this.lastname = lastname;
        Email = email;
        this.date = date;
        this.age = age;
        this.country = country;
        this.Anydisease1 = anydisease1;
        this.Anydisease2 = anydisease2;
        this.Anydisease3 = anydisease3;
    }


    public Data(int id, String firstname, String lastname, String email, String date, int age, String gender, String country, String userid, String pascode, String anydisease1, String anydisease2, String anydisease3) {
        this.id = id;
        Firstname = firstname;
        this.lastname = lastname;
        Email = email;
        this.date = date;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.userid = userid;
        this.pascode = pascode;
        Anydisease1 = anydisease1;
        Anydisease2 = anydisease2;
        Anydisease3 = anydisease3;
    }

    public Data(String firstname, String lastname, String email, String date, int age, String gender, String country, String userid, String pascode) {
        Firstname = firstname;
        this.lastname = lastname;
        Email = email;
        this.date = date;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.userid = userid;
        this.pascode = pascode;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPascode() {
        return pascode;
    }

    public void setPascode(String pascode) {
        this.pascode = pascode;
    }



}
