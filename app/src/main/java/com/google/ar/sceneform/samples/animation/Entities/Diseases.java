package com.google.ar.sceneform.samples.animation.Entities;

public class Diseases {

    public int scoliosis;
    public int kyphosis;
    public int lordosis;
    public int kneeValgus;
    public int kneeVarus;
    public String id;

    public Diseases(){}

    public void setKyphosis(int kyphosis) {
        this.kyphosis = kyphosis;
    }

    public void setScoliosis(int scoliosis) {
        this.scoliosis = scoliosis;
    }

    public void setLordosis(int lordosis) {
        this.lordosis = lordosis;
    }

    public void setKneeValgus(int kneeValgus) {
        this.kneeValgus = kneeValgus;
    }

    public void setKneeVarus(int kneeVarus) {
        this.kneeVarus = kneeVarus;
    }

    public int getKyphosis() {
        return kyphosis;
    }

    public int getScoliosis() {
        return scoliosis;
    }

    public int getLordosis() {
        return lordosis;
    }

    public int getKneeValgus() {
        return kneeValgus;
    }

    public int getKneeVarus() {
        return kneeVarus;
    }
}
