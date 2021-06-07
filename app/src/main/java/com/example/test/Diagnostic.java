package com.example.test;


import com.google.mlkit.vision.pose.PoseLandmark;

import static java.lang.Math.atan2;


// Nu stiu inca exact cum voi folosi asta. Trebuie sa fac legatura cumva cu baza de date
public class Diagnostic {

    private double scoliosis;
    private double kyphosis;
    private double lordosis;
    private double roundedShoulders;
    private double kneeValgus;
    private double kneeVarus;

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

    // Method that returns the angle between shoulders and hips in case of setScoliosis:
    static double[] setScoliosis(PoseLandmark upperShoulder, PoseLandmark lowerShoulder, PoseLandmark upperHip, PoseLandmark lowerHip){

        double projectionX = upperShoulder.getPosition().x;
        double projectionY = lowerShoulder.getPosition().y;

        double shoulderAngle = getAngle2(upperShoulder.getPosition().x, upperShoulder.getPosition().y,
                lowerShoulder.getPosition().x, lowerShoulder.getPosition().y, projectionX, projectionY);

        projectionX = upperHip.getPosition().x;
        projectionY = lowerHip.getPosition().y;

        double hipAngle = getAngle2(upperHip.getPosition().x, upperHip.getPosition().y,
                lowerHip.getPosition().x, lowerHip.getPosition().y, projectionX, projectionY);

        double[] angles = new double[2];
        angles[0] = shoulderAngle;
        angles[1] = hipAngle;

        return angles;
        // de adaugat: setare valoare scoliosis cu probabilitatea...
        // setare valoare unghiuri...
    }

}
