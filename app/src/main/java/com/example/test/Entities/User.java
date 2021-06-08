package com.example.test.Entities;

import java.io.Serializable;

public class User implements Serializable {

    public String fullName;
    public String age;
    public String email;
    public Diagnostic[] diagnoses;

    public User() {

    }

    public User(String fullName, String age, String email) {

        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }
}
