package com.example.test.Entities;

import java.io.Serializable;

public class DiagnosticHasAngles implements Serializable {

    public String diagnosticId;
    public String anglesId;

    public DiagnosticHasAngles(String anglesId, String diagnosticId){
        this.anglesId = anglesId;
        this.diagnosticId = diagnosticId;
    }
}
