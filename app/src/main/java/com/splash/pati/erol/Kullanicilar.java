package com.splash.pati.erol;

/**
 * Created by Aceer on 20.04.2018.
 */

public class Kullanicilar {
    String name;
    String city;
    String sex;
    String date;

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getSex() {
        return sex;
    }

    public String getDate() {
        return date;
    }

    public Kullanicilar(String name, String city, String sex, String date) {
        this.name = name;
        this.city = city;
        this.sex = sex;
        this.date = date;
    }
    public Kullanicilar(){}
}
