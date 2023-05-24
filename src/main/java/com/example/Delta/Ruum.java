package com.example.Delta;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.Map;


public class Ruum {
    private final String highlightStyle = "-fx-fill: #ffbd4a; -fx-opacity: 0.6;";

    private int number;
    private String info;
    private final int mahutavus;
    private double x_alumine;
    private double y_alumine;
    private double x_ylemine;
    private double y_ylemine;

    private int korrus;

    public static Map<Integer, Ruum> ruumid = new HashMap<>();

    /**
     * Loob ringi, mis sisaldab infot ruumi kohta ning asub ruumi antud koordinaatidel.
     * @return Group, mis sisaldab ringi ennast ning seesolevat teksti.
     */
    public Group ruumiKuva() {
        Text text = new Text(number + "\n" + info);
        if (info.equals("N/A")) text.setText(String.valueOf(number));
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.BLACK);
        double textWidth = text.getBoundsInLocal().getWidth();

        if (number == 1015) {
            Polygon kolmnurk = new Polygon();
            kolmnurk.getPoints().addAll(x_ylemine,y_ylemine,x_alumine,y_alumine, 649.0,172.0);
            double keskX = ((kolmnurk.getPoints().get(0)+kolmnurk.getPoints().get(2))/3+ 175);
            double keskY = ((kolmnurk.getPoints().get(1)+kolmnurk.getPoints().get(5))/3);
            text.setX(keskX);
            text.setY(keskY);
            kolmnurk.setStyle(highlightStyle);
            return new Group(kolmnurk, text);
        } else {
            Rectangle ruut = new Rectangle();
            ruut.setX(x_ylemine);
            ruut.setY(y_ylemine);
            ruut.setWidth(x_alumine - x_ylemine);
            ruut.setHeight(y_alumine - y_ylemine);
            ruut.setStyle(highlightStyle);

            text.setX((ruut.getX() + ruut.getWidth() / 2) - textWidth / 2);
            text.setY(ruut.getY() + ruut.getHeight() / 2);
            return new Group(ruut, text);
        }
    }


    public boolean onRuudus(double X, double Y) {
        return (ruumiKuva().contains(X, Y));
    }

    public Ruum(int number, String info, int mahutavus, double x_ylemine, double y_ylemine,double x_alumine,double y_alumine) {
        this.number = number;
        this.info = info;
        this.mahutavus = mahutavus;
        this.x_ylemine = x_ylemine;
        this.y_ylemine = y_ylemine;
        this.x_alumine = x_alumine;
        this.y_alumine = y_alumine;
        this.korrus = number/1000;
        ruumid.put(number, this);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getKorrus() { return korrus;}

    public int getMahutavus() {
        return mahutavus;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getX_ylemine() { return x_ylemine;
    }

    public double getY_ylemine() { return y_ylemine;
    }
}
