package com.example.test.Entities;

import java.io.Serializable;

public class DiagnosticHasAngles implements Serializable {

    public String diagnosticId;
    public String anglesId;

    public DiagnosticHasAngles(){}
    public DiagnosticHasAngles(String anglesId, String diagnosticId){
        this.anglesId = anglesId;
        this.diagnosticId = diagnosticId;
    }

    public String getDiagnosticId() {
        return diagnosticId;
    }

    public String getAnglesId() {
        return anglesId;
    }
}
