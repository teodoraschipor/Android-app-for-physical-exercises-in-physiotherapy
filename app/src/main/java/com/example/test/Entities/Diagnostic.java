package com.example.test.Entities;


import com.google.mlkit.vision.pose.PoseLandmark;

import java.io.Serializable;
import java.util.Date;

import static java.lang.Math.atan2;


// Nu stiu inca exact cum voi folosi asta. Trebuie sa fac legatura cumva cu baza de date
public class Diagnostic implements Serializable {

    public String imagePath;
    public Angles angles;
    public Date date;

    public Diagnostic(String imagePath){
        this.imagePath = imagePath;
        this.angles = new Angles();
        this.date = new Date();
    }

    public void setAngles(Angles angles) {
        this.angles = angles;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
