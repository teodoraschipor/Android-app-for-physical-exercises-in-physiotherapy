package com.google.ar.sceneform.samples.animation.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Diagnostic implements Serializable {

    public String imagePath;
    public Date date;
    public String id;
    public Diseases diseases;

    public Diagnostic(){

    }
    public Diagnostic(String imagePath, Date date, String id, Diseases diseases){
        this.imagePath = imagePath;
        this.date = date;
        this.id = id;
    }


    public void setImagePath(String imagePath){ this.imagePath = imagePath;    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Date getDate() { return date; }

    public Diseases getDiseases() {
        return diseases;
    }

    public void setDiseases(Diseases diseases) {
        this.diseases = diseases;
    }

    public List<Integer> diseasesGrade(Angles angles) {
        List<Integer> diseasesGrade = new ArrayList<>();
        double kyphosisAngle = angles.getKyphosis();
        double scoliosisAngle = angles.getScoliosis();
        double lordosisAngle = angles.getLordosis();
        double kneeValgusAngle = angles.getKneeValgus();
        double kneeVarusAngle = angles.getKneeVarus();

        // KYPHOSIS
        if(kyphosisAngle < 0.5){
            diseasesGrade.add(0);
        } else if(kyphosisAngle <2) {
            diseasesGrade.add(1);
        } else if(kyphosisAngle < 5) {
            diseasesGrade.add(2);
        } else {
            diseasesGrade.add(3);
        }

        // SCOLIOSIS
        if(scoliosisAngle < 0.5){
            diseasesGrade.add(0);
        } else if(scoliosisAngle <2) {
            diseasesGrade.add(1);
        } else if(scoliosisAngle < 5) {
            diseasesGrade.add(2);
        } else {
            diseasesGrade.add(3);
        }

        // LORDOSIS
        if(lordosisAngle < 0.5){
            diseasesGrade.add(0);
        } else if(lordosisAngle <2) {
            diseasesGrade.add(1);
        } else if(lordosisAngle < 5) {
            diseasesGrade.add(2);
        } else {
            diseasesGrade.add(3);
        }

        // KNEE VALGUS
        if(kneeValgusAngle < 0.5){
            diseasesGrade.add(0);
        } else if(kneeValgusAngle <2) {
            diseasesGrade.add(1);
        } else if(kneeValgusAngle < 5) {
            diseasesGrade.add(2);
        } else {
            diseasesGrade.add(3);
        }
        // KNEE VALGUS
        if(kneeVarusAngle < 0.5){
            diseasesGrade.add(0);
        } else if(kneeVarusAngle <2) {
            diseasesGrade.add(1);
        } else if(kneeVarusAngle < 5) {
            diseasesGrade.add(2);
        } else {
            diseasesGrade.add(3);
        }

        return diseasesGrade;
    }



    public String getImagePath() {
        return imagePath;
    }

    public void setId(String id) {
        this.id = id;
    }

    // METHODS
}
