package com.example.demo;

import javafx.scene.shape.Rectangle;

public class Ruum {
    private String nimi;
    private double x;
    private double y;
    private double width;
    private double height;

    public Ruum(String nimi, double x, double y, double width, double height) {
        this.nimi = nimi;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public boolean contains(double pointX, double pointY) {
        return pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle highlightRuum(){
        return new Rectangle(x,y,width,height);
    }
}
