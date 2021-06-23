package com.example.test.Entities;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.io.Serializable;

import static java.lang.Math.atan2;

public class Angles implements Serializable {

    public double scoliosis;
    public double kyphosis;
    public double lordosis;
    public double kneeValgus;
    public double kneeVarus;


    //------------CONSTRUCTORS-----------
    public Angles(){

    }

    //------------METHODS-------------

    // Method that returns the angle between 3 PoseLandmark points:
    static double getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double result =
                Math.toDegrees(
                        atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                                lastPoint.getPosition().x - midPoint.getPosition().x)
                                - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                                firstPoint.getPosition().x - midPoint.getPosition().x));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }


    // Method that returns the angle between 3 points that doesn't suppose to be PoseLarndmarks, but to have X and Y coordinates:
    static double getAngle2(double firstPointX, double firstPointY, double midPointX, double midPointY, double lastPointX, double lastPointY) {
        double result =
                Math.toDegrees(
                        atan2(lastPointY - midPointY,
                                lastPointX - midPointX)
                                - atan2(firstPointY- midPointY,
                                firstPointX - midPointX));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }

    //-------------GETTERS & SETTERS---------------

    public double getScoliosis() {
        return scoliosis;
    }

    public void setScoliosis(PoseLandmark upperShoulder, PoseLandmark lowerShoulder, PoseLandmark upperHip, PoseLandmark lowerHip) {
        double projectionX = upperShoulder.getPosition().x;
        double projectionY = lowerShoulder.getPosition().y;

        double shoulderAngle = getAngle2(upperShoulder.getPosition().x, upperShoulder.getPosition().y,
                lowerShoulder.getPosition().x, lowerShoulder.getPosition().y, projectionX, projectionY);

        projectionX = upperHip.getPosition().x;
        projectionY = lowerHip.getPosition().y;

        double hipAngle = getAngle2(upperHip.getPosition().x, upperHip.getPosition().y,
                lowerHip.getPosition().x, lowerHip.getPosition().y, projectionX, projectionY);

        double angle = Math.max(shoulderAngle, hipAngle);

        this.scoliosis = angle;
    }

    public void setKyphosis(double x){
        this.kyphosis = x;
    }
}
