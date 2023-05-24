package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Ruum {
    String highlightStyle = "-fx-fill: #ffbd4a; -fx-opacity: 0.6;";

    private int number;
    private String info;
    private final int mahutavus;
    private double x;
    private double y;
    private final double raadius;

    public static Map<Integer, Ruum> ruumid = new HashMap<>();

    public Ruum(int number, String info, int mahutavus, double x, double y, double raadius) {
        this.number = number;
        this.info = info;
        this.mahutavus = mahutavus;
        this.x = x;
        this.y = y;
        this.raadius = raadius;
        ruumid.put(number, this);
    }

    public Group ring() {
        Circle ring = new Circle(raadius);
        ring.setCenterX(x);
        ring.setCenterY(y);
        ring.setStyle(highlightStyle);

        Text text = new Text(number + "\n" + info);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.BLACK);

        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();
        text.setLayoutX(ring.getCenterX() - (textWidth / 2));
        text.setLayoutY(ring.getCenterY() + (textHeight / 4));
        return new Group(ring, text);
    }


    public boolean onRingis(double pointX, double pointY) {
        var dx = abs(pointX - x);
        var dy = abs(pointY - y);
        if (dx > raadius) return false;
        else return !(dy > raadius);
    }

    public int getNumber() {
        return number;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setX(double x) {
        this.x = x;

    }

    public void setY(double y) {
        this.y = y;
    }

    public int getMahutavus() {
        return mahutavus;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
