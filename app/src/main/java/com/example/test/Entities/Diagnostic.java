package com.example.test.Entities;


import android.net.Uri;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.io.Serializable;
import java.util.Date;

import static java.lang.Math.atan2;


// Nu stiu inca exact cum voi folosi asta. Trebuie sa fac legatura cumva cu baza de date
public class Diagnostic implements Serializable {

   // public Uri imageUri;
    public String imagePath;
  //  public Angles angles;
    public Date date;
    public String id;

    public Diagnostic(){

    }
    public Diagnostic(String imagePath, Date date, String id){
        this.imagePath = imagePath;
        this.date = date;
        this.id = id;
    }

  //  public void setImagePath(Uri imageUri){this.imageUri = imageUri;}
    public void setImagePath(String imagePath){ this.imagePath = imagePath;    }
    //public void setAngles(Angles angles) {this.angles = angles;}
    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Date getDate() { return date; }

    //  public Uri getImageUri() { return imageUri; }


    public String getImagePath() {
        return imagePath;
    }

    public void setId(String id) {
        this.id = id;
    }
}
