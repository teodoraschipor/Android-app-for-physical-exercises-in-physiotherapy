package com.example.test.Entities;

import java.io.Serializable;

public class UserIsDiagnosed implements Serializable {

    public String userId;
    public String diagnosticId;

    public UserIsDiagnosed(String userId, String diagnosticId){
        this.userId = userId;
        this.diagnosticId = diagnosticId;
    }
}
