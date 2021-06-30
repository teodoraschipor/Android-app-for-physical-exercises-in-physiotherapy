package com.google.ar.sceneform.samples.animation.Entities;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Diseases {

    List<Integer> list = new ArrayList<>();
    public int maximum;
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

    public void getMax(){

        list.add(kyphosis);
        list.add(scoliosis);
        list.add(lordosis);
        list.add(kneeValgus);
        list.add(kneeVarus);

        maximum = list.get(0);
        for (int i=1; i<list.size(); i++) {
            if (list.get(i) > maximum) {
                maximum = list.get(i);   // new maximum
            }
        }

    }

    public List<String> getMaxList(){

        List<String> maxList = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            if (list.get(i) == maximum) {
                if(i == 0){
                    maxList.add("kyphosis: ");
                } else if(i == 1){
                    maxList.add("scoliosis:" );
                }else if(i == 2){
                    maxList.add("lordosis: ");
                }else if(i == 3){
                    maxList.add("knee valgus: ");
                }else if(i == 4){
                    maxList.add("knee varus: ");
                }
            }
        }

        return maxList;
    }
}
