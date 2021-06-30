package com.google.ar.sceneform.samples.animation.Entities;

import com.google.mlkit.vision.pose.PoseLandmark;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.atan2;

public class Angles implements Serializable {

    public double kyphosis;
    public double scoliosis;
    public double lordosis;
    public double kneeValgus;
    public double kneeVarus;
    public String id;

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
        //  if (result > 180) {
        //    result = (360.0 - result); // Always get the acute representation of the angle
        // }
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


    public double kneeAngle(List<PoseLandmark> allPoseLandmarks){
        /*
         * leftKnee = 25
         * rightKnee = 26
         * */
        PoseLandmark leftHip= allPoseLandmarks.get(23);
        PoseLandmark rightHip = allPoseLandmarks.get(24);
        PoseLandmark leftKnee= allPoseLandmarks.get(25);
        PoseLandmark rightKnee = allPoseLandmarks.get(26);
        PoseLandmark leftAnkle = allPoseLandmarks.get(27);
        PoseLandmark rightAnkle = allPoseLandmarks.get(28);

        double angleLeft = getAngle(leftHip,
                leftKnee,
                leftAnkle);

        double angleRight = getAngle(rightHip,
                rightKnee,
                rightAnkle);

        double newAngleRight = 180 - angleRight + 180;
        double average = (angleLeft + newAngleRight) / 2;
        return average;
    }
    //-------------GETTERS & SETTERS---------------


    public String getId() { return id; }

    public double getScoliosis() {
        return scoliosis;
    }

    public double getKyphosis(){ return kyphosis; }

    public double getLordosis() { return lordosis; }

    public double getKneeValgus() { return kneeValgus; }

    public double getKneeVarus() { return kneeVarus; }

    public void setId(String id) { this.id = id; }

    public void setScoliosis(List<PoseLandmark> allPoseLandmarks) {

        PoseLandmark upperShoulder, lowerShoulder, upperHip, lowerHip;
        /* Positions in List:
         * leftShoulder =11
         * rightShoulder =12
         * leftHip =23
         * rightHip =24
         * */
        PoseLandmark leftShoulder = allPoseLandmarks.get(11);
        PoseLandmark rightShoulder = allPoseLandmarks.get(12);
        PoseLandmark leftHip = allPoseLandmarks.get(23);
        PoseLandmark rightHip =  allPoseLandmarks.get(24);

        // if the left shoulder is upper than the right shoulder
        if(leftHip.getPosition().y > rightHip.getPosition().y){
            upperHip = leftHip;
            lowerHip = rightHip;
        } else {
            upperHip = rightHip;
            lowerHip = leftHip;
        }

        if(leftShoulder.getPosition().y > rightShoulder.getPosition().y){
            upperShoulder = leftShoulder;
            lowerShoulder = rightShoulder;
        } else {
            upperShoulder = rightShoulder;
            lowerShoulder = leftShoulder;
        }

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

    public void setKyphosis(List<PoseLandmark> allPoseLandmarks){

        /*
         * rightEar = 8
         * leftEar = 7
         * */
        PoseLandmark leftEar = allPoseLandmarks.get(7);
        PoseLandmark rightEar = allPoseLandmarks.get(8);
        PoseLandmark leftShoulder = allPoseLandmarks.get(11);
        PoseLandmark rightShoulder = allPoseLandmarks.get(12);
        PoseLandmark leftAnkle = allPoseLandmarks.get(27);
        PoseLandmark rightAnkle = allPoseLandmarks.get(28);
        Float newPointZ;
        Float newPointY;

        // Angle between left ear, left ankle and newPoint
        newPointZ = leftAnkle.getPosition3D().getZ();
        newPointY = leftEar.getPosition().y;

        double angleLeftEar = getAngle2(leftEar.getPosition3D().getZ(), leftEar.getPosition3D().getY(),
                leftAnkle.getPosition3D().getZ(), leftAnkle.getPosition3D().getY(),
                newPointZ, newPointY);

        // Angle between right ear, right ankle and newPoint
        newPointZ = rightAnkle.getPosition3D().getZ();
        newPointY = rightEar.getPosition().y;
        double angleRightEar = getAngle2(rightEar.getPosition3D().getZ(), rightEar.getPosition3D().getY(),
                rightAnkle.getPosition3D().getZ(), rightAnkle.getPosition3D().getY(),
                newPointZ, newPointY);


        // Angle between left shoulder, left ankle and newPoint
        newPointZ = leftAnkle.getPosition3D().getZ();
        newPointY = leftShoulder.getPosition().y;

        double angleLeftShoulder = getAngle2(leftShoulder.getPosition3D().getZ(), leftShoulder.getPosition3D().getY(),
                leftAnkle.getPosition3D().getZ(), leftAnkle.getPosition3D().getY(),
                newPointZ, newPointY);

        // Angle between right shoulder, right ankle and newPoint
        newPointZ = rightAnkle.getPosition3D().getZ();
        newPointY = rightShoulder.getPosition().y;

        double angleRightShoulder = getAngle2(rightShoulder.getPosition3D().getZ(), rightShoulder.getPosition3D().getY(),
                rightAnkle.getPosition3D().getZ(), rightAnkle.getPosition3D().getY(),
                newPointZ, newPointY);

        double average = (angleLeftEar + angleRightEar + angleLeftShoulder + angleRightShoulder) / 4;
        this.kyphosis = average;

    }

    public void setLordosis(List<PoseLandmark> allPoseLandmarks) {

        /*
         * rightAnkle = 28
         * leftAnkle = 27
         * */
        PoseLandmark leftEar = allPoseLandmarks.get(7);
        PoseLandmark rightEar = allPoseLandmarks.get(8);
        PoseLandmark leftHip= allPoseLandmarks.get(23);
        PoseLandmark rightHip = allPoseLandmarks.get(24);
        PoseLandmark leftAnkle = allPoseLandmarks.get(27);
        PoseLandmark rightAnkle = allPoseLandmarks.get(28);
        Float newPointZ;
        Float newPointY;

        // Angle between left hip, left ankle and newPoint
        newPointZ = leftAnkle.getPosition3D().getZ();
        newPointY = leftHip.getPosition().y;

        double angleLeft = getAngle2(leftHip.getPosition3D().getZ(), leftHip.getPosition3D().getY(),
                leftAnkle.getPosition3D().getZ(), leftAnkle.getPosition3D().getY(),
                newPointZ, newPointY);

        // Angle between right shoulder, right ankle and newPoint
        newPointZ = rightAnkle.getPosition3D().getZ();
        newPointY = rightHip.getPosition().y;

        double angleRight = getAngle2(rightHip.getPosition3D().getZ(), rightHip.getPosition3D().getY(),
                rightAnkle.getPosition3D().getZ(), rightAnkle.getPosition3D().getY(),
                newPointZ, newPointY);

        double average = (angleLeft + angleRight) / 2;
        this.lordosis = 180 - average;

    }

    public void setKneeValgus(List<PoseLandmark> allPoseLandmarks) {

        double average = kneeAngle(allPoseLandmarks);
        double angle = 180 - average;
        if( angle > 0 ){
            this.kneeValgus = angle;
        }
        else {
            this.kneeValgus = 0;
        }
    }

    public void setKneeVarus(List<PoseLandmark> allPoseLandmarks) {


        double average = kneeAngle(allPoseLandmarks);
        double angle = average - 180;
        if (angle > 0) {
            this.kneeVarus = angle;
        } else {
            this.kneeVarus = 0;
        }
    }
}
