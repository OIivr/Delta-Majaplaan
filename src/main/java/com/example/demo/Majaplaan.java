package com.example.demo;

import java.io.*;
import java.text.SimpleDateFormat;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Majaplaan extends Application {

    Label label;
    String logi = "";
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    public void start(Stage stage) {


        Map<Integer, Ruum> ruumid = Ruum.ruumid;

        Image image = new Image("korrus1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(imageView);

        try {
            Scanner lugeja = new Scanner(new File("ruumid.txt"));
            while (lugeja.hasNextLine()) {
                String[] rida = lugeja.nextLine().split(";");
                new Ruum(Integer.parseInt(rida[0]), rida[1], Integer.parseInt(rida[2]), Double.parseDouble(rida[3]), Double.parseDouble(rida[4]), Double.parseDouble(rida[5]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        TextField textField = new TextField("Sisesta otsitava ruumi number:");
        textField.setMinWidth(300);
        Button button = new Button("Otsi");


        label = new Label();
        label.setStyle("-fx-border-color: #000000;-fx-border-width: 2;");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(240, 226, 172), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(580);
        label.setLayoutY(300);
        label.setVisible(false);
        anchorPane.getChildren().add(label);

        textField.setLayoutX(10);
        textField.setLayoutY(10);
        textField.setPrefWidth(100);
        button.setLayoutX(120);
        button.setLayoutY(10);
        button.setOnAction(event -> {
            try {
                int number = Integer.parseInt(textField.getText());
                if (!ruumid.containsKey(number)) {
                    throw new ValeOtsingErind("Ruumi numbriga " + number + " ei leitud!");
                } else {
                    kuvaRuumRingiga(anchorPane, ruumid.get(number));
                    label.setText("Ruum " + ruumid.get(number).getNumber() + "\n\n" + ruumid.get(number).getInfo() + "\n\n" + "Mahutavus: " + ruumid.get(number).getMahutavus());
                    label.setVisible(true);
                    logi += "\nAeg: " + aeg() + "\nKasutajale kuvati ruumi " + ruumid.get(number).getNumber() + " asukoht ja info.\n";
                }
            } catch (NumberFormatException e) {
                String sisend = textField.getText();
                label.setText("Sisend: \"" + sisend + "\" ei ole arv!");
                label.setVisible(true);
                logi += "\nAeg: " + aeg() + "\nKasutaja sisestas vales formaadis sisendi,\nKasutajale edastati sõnum:\nSisend: \"" + sisend + "\" ei ole arv!\n";
            } catch (ValeOtsingErind e) {
                label.setText(e.getMessage());
                label.setVisible(true);
                logi += "\nAeg: " + aeg() + "\nKasutaja sisestas numbri, millele vastavat ruumi ei leidu,\nKasutajale edastati sõnum:\n" + e.getMessage() + "\n";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                button.fire();
            }
        });

        imageView.setOnMouseClicked(event -> {
            for (Ruum ruum : ruumid.values()) {
                if (ruum.onRingis(event.getX(), event.getY())) {
                    try {
                        kuvaRuumRingiga(anchorPane, ruum);
                        logi += "\nAeg: " + aeg() + "\nKasutajale kuvati ruumi " + ruum.getNumber() + " asukoht ja info.\n";
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        anchorPane.getChildren().addAll(textField, button);
        Scene scene = new Scene(new VBox(new HBox(textField, button), anchorPane));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            try {
                FileWriter kirjutaja = new FileWriter(new File("logi.txt"));
                kirjutaja.write(logi);
                kirjutaja.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("logi on salvestatud faili logi.txt");
        });
        stage.show();
    }

    private String aeg() {
        SimpleDateFormat formaat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String aeg = formaat.format(new Date());
        return aeg;
    }

    public void kuvaRuumRingiga(AnchorPane root, Ruum ruum) throws InterruptedException {
        Group ring = ruum.ring();
        root.getChildren().add(ring);
        label.setText("Ruum " + ruum.getNumber() + "\n\n" + ruum.getInfo() + "\n\n" + "Mahutavus: " + ruum.getMahutavus());
        label.setVisible(true);
        PauseTransition paus = new PauseTransition(Duration.seconds(3));
        paus.setOnFinished(e -> root.getChildren().remove(ring));
        paus.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
