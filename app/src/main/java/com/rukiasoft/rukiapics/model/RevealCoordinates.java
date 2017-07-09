package com.rukiasoft.rukiapics.model;

/**
 * Created by Roll on 10/7/17.
 */

public class RevealCoordinates {
    private int cx, cy;
    private float initialRadius, endRadius;

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public float getInitialRadius() {
        return initialRadius;
    }

    public void setInitialRadius(float initialRadius) {
        this.initialRadius = initialRadius;
    }

    public float getEndRadius() {
        return endRadius;
    }

    public void setEndRadius(float endRadius) {
        this.endRadius = endRadius;
    }
}
