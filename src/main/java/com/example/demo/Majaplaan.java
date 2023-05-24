package com.example.ruhmatoo2;

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
    int kuvatavKorrus;
    AnchorPane anchorPane = new AnchorPane();
    Map<Integer, Ruum> ruumid = Ruum.ruumid;

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    public void start(Stage stage) {

        logi += "\nAeg: " + aeg() + "\nKasutaja avas rakenduse.\n";

        Map<Integer, Ruum> ruumid = Ruum.ruumid;
        kuvaKorrus(1);

        try { // Failist ruumide info lugemine
            Scanner lugeja = new Scanner(new File("ruumid.txt"));
            while (lugeja.hasNextLine()) {
                String[] rida = lugeja.nextLine().split(";");
                new Ruum(Integer.parseInt(rida[0]), rida[1], Integer.parseInt(rida[2]), Double.parseDouble(rida[3]), Double.parseDouble(rida[4]), Double.parseDouble(rida[5]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Otsinguriba
        TextField textField = new TextField("Sisesta otsitava ruumi number:");
        textField.setMinWidth(300);

        //Otsingunupp
        Button otsingNupp = new Button("Otsi");

        //Korrusenupud
        Button korrus1Nupp = new Button("1. Korrus");
        Button korrus2Nupp = new Button("2. Korrus");
        Button korrus3Nupp = new Button("3. Korrus");
        Button korrus4Nupp = new Button("4. Korrus");
        Button[] nupud = new Button[4];
        nupud[0] = korrus1Nupp;
        nupud[1] = korrus2Nupp;
        nupud[2] = korrus3Nupp;
        nupud[3] = korrus4Nupp;
        for (Button nupp : nupud) {
            nupp.setLayoutX(130);
            nupp.setLayoutY(10);
            if (nupp == korrus1Nupp) nupp.setOnAction(event -> {
                kuvaKorrus(1);
            });
            else if (nupp == korrus2Nupp) nupp.setOnAction(event -> {
                kuvaKorrus(2);
            });
            else if (nupp == korrus3Nupp) nupp.setOnAction(event -> {
                kuvaKorrus(3);
            });
            else if (nupp == korrus4Nupp) nupp.setOnAction(event -> {
                kuvaKorrus(4);
            });
        }

        textField.setLayoutX(10);
        textField.setLayoutY(10);
        textField.setPrefWidth(100);
        otsingNupp.setLayoutX(130);
        otsingNupp.setLayoutY(10);
        otsingNupp.setOnAction(event -> { //Ruumi numbri otsingusse sisestamine, hakkab tööle nupuvajutusel
            try {
                int number = Integer.parseInt(textField.getText());
                if (!ruumid.containsKey(number)) {
                    throw new ValeOtsingErind("Ruumi numbriga " + number + " ei leitud!");
                } else {
                    if (kuvatavKorrus != number / 1000) kuvaKorrus(number / 1000);
                    kuvaRuumRingiga(anchorPane, ruumid.get(number));
                    label.setText("Ruum " + ruumid.get(number).getNumber() + "\n\n" + ruumid.get(number).getInfo() + "\n\n" + "Mahutavus: " + ruumid.get(number).getMahutavus());
                    label.setVisible(true);
                    logi += "\nAeg: " + aeg() + "\nKasutajale kuvati ruumi " + ruumid.get(number).getNumber() + " asukoht ja info.\n";
                }
            } catch (NumberFormatException e) {
                String sisend = textField.getText();
                label.setText("Sisend: \"" + sisend + "\"\nei ole arv!");
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

        textField.setOnKeyPressed(event -> { //Ruumi numbri otsingusse sisestamine juhul kui kasutaja vajutas "Enter" klahvi
            if (event.getCode() == KeyCode.ENTER) {
                otsingNupp.fire();
            }
        });

        anchorPane.getChildren().addAll(textField, otsingNupp, korrus1Nupp, korrus2Nupp, korrus3Nupp, korrus4Nupp);
        Scene scene = new Scene(new VBox(new HBox(textField, otsingNupp, korrus1Nupp, korrus2Nupp, korrus3Nupp, korrus4Nupp), anchorPane));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> { //Logifaili loomine
            try {
                Writer kirjutaja = new FileWriter("logi.txt", true);
                kirjutaja.write(logi);
                kirjutaja.close();
            } catch (FileNotFoundException e) {
                try {
                    FileWriter kirjutaja = new FileWriter(new File("logi.txt"));
                    kirjutaja.write(logi);
                    kirjutaja.close();
                } catch (IOException s) {
                    throw new RuntimeException(s);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("logi on salvestatud faili logi.txt");
        });
        stage.show();
    }

    private String aeg() { //Õige formaadiga aja saamine
        SimpleDateFormat formaat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String aeg = formaat.format(new Date());
        return aeg;
    }

    public void kuvaRuumRingiga(AnchorPane root, Ruum ruum) throws InterruptedException { //Infoga ringi kuvamine
        if (ruum.getKorrus() == kuvatavKorrus) {
            Group ring = ruum.ring();
            root.getChildren().add(ring);
            label.setText("Ruum " + ruum.getNumber() + "\n\n" + ruum.getInfo() + "\n\n" + "Mahutavus: " + ruum.getMahutavus());
            label.setVisible(true);
            PauseTransition paus = new PauseTransition(Duration.seconds(3));
            paus.setOnFinished(e -> root.getChildren().remove(ring));
            paus.play();
        }
    }

    public void kuvaKorrus(int korrus) {
        Image image = null;
        logi+= "\nAeg: " + aeg() + "\nKasutajale kuvati korrus " + korrus +".\n";
        if (korrus == 1) {
            kuvatavKorrus = 1;
            image = new Image("korrus1.jpg");

        } else if (korrus == 2) {
            kuvatavKorrus = 2;
            image = new Image("korrus2.jpg");

        } else if (korrus == 3) {
            kuvatavKorrus = 3;
            image = new Image("korrus3.jpg");

        } else if (korrus == 4) {
            kuvatavKorrus = 4;
            image = new Image("korrus4.jpg");
        }

        //Pildi sättimine taustaks
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);
        anchorPane.getChildren().add(imageView);

        //Infot kuvav kast
        label = new Label();
        label.setStyle("-fx-border-color: #000000;-fx-border-width: 2;");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(240, 226, 172), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(580);
        label.setMaxWidth(200);
        label.setLayoutY(300);
        label.setVisible(false);
        anchorPane.getChildren().add(label);

        //Ruumil klõpsamisel selle info kuvamine
        imageView.setOnMouseClicked(event -> { //Ruumi info kuvamine juhul kui kasutaja vajutas ruumi peale.
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

    }

    public static void main(String[] args) {
        launch(args);
    }
}
