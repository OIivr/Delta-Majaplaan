package com.example.demo;

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
import java.util.Map;



public class Majaplaan extends Application {

    Label label;
    @Override
    public void start(Stage stage) {

        Map<Integer, Ruum> ruumid = Ruum.ruumid;

        Image image = new Image("korrus1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(imageView);

        Ruum r1037 = new Ruum(1037,"Suur auditoorium",301,64.0, 132.0,50);
        Ruum r1020 = new Ruum(1020,"Auditoorium",100,355.0, 155.0,40);
        Ruum r1021 = new Ruum(1021,"Auditoorium",200,273.0, 155.0,40);
        Ruum r1022 = new Ruum(1022,"Auditoorium",40,202.0, 150.0,30);
        Ruum r1025 = new Ruum(1025,"Seminariruum",30,169.0, 319.0,30);
        Ruum r1024 = new Ruum(1024,"Seminariruum",30,181.0, 270.0,30);
        Ruum r1026 = new Ruum(1026,"Seminariruum",30,156.0,363.0,30);
        Ruum r1019 = new Ruum(1019,"Auditoorium",100,424.0, 159.0,40);
        Ruum r1018 = new Ruum(1018,"Auditoorium",100,497.0, 155.0,40);
        Ruum r1017 = new Ruum(1017,"Auditoorium",40,559.0, 163.0,30);
        Ruum r1008 = new Ruum(1008,"Auditoorium",60,520.0, 73.0,30);
        Ruum r1007 = new Ruum(1007,"Auditoorium",60,456.0, 72.0,30);
        Ruum r1006 = new Ruum(1006,"Auditoorium",60,396.0, 73.0,30);
        Ruum r1005 = new Ruum(1005,"Auditoorium",60,335.0, 73.0,30);
        Ruum r1004 = new Ruum(1004,"Auditoorium",40,273.0, 78.0,30);

        Ruum r1027 = new Ruum(1027,"N/A",0,134.0,391.0,10);
        Ruum r1030A = new Ruum(1032,"sTARTUp Lab",0,139.0,435.0,30);
        Ruum r1030 = new Ruum(1030,"Demokeskus",40,126.0, 484.0,30);
        Ruum r1031 = new Ruum(1031,"N/A",0,111.0, 531.0,25);

        Ruum r1016 = new Ruum(1016,"N/A",0, 273.0, 78.0,10);
        Ruum r1013 = new Ruum(1013,"Raamatukogu",0,633.0, 97.0,30);
        Ruum r1009 = new Ruum(1009,"N/A",0,557.0, 57.0,15);
        Ruum r1015 = new Ruum(1015,"Raamatukogu",80,691.0, 71.0,50);

        Ruum r1039 = new Ruum(1039,"Arvutimuuseum",0,41.0, 44.0,30);
        Ruum r1040 = new Ruum(1040,"Kabinett",0,79.0, 34.0,10);

        TextField textField = new TextField("Sisesta otsitava ruumi number:");
        textField.setMinWidth(300);
        Button button = new Button("Otsi");

        label = new Label();
        label.setStyle("-fx-border-color: #000000;-fx-border-width: 2;");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(240,226,172), CornerRadii.EMPTY, Insets.EMPTY)));
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
                    label.setText("Ruum " + ruumid.get(number).getNumber()+"\n\n"+ruumid.get(number).getInfo()+"\n\n"+"Mahutavus: " + ruumid.get(number).getMahutavus());
                    label.setVisible(true);
                }
            } catch (NumberFormatException e) {
                String sisend = textField.getText();
                System.out.println("Sisend: \"" + sisend + "\" ei ole arv!");
            } catch (ValeOtsingErind e) {
                System.out.println(e.getMessage());
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
                    kuvaRuumRingiga(anchorPane, ruum);
                }
            }
        });

        anchorPane.getChildren().addAll(textField, button);
        Scene scene = new Scene(new VBox(new HBox(textField,button),anchorPane));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void kuvaRuumRingiga(AnchorPane root,Ruum ruum) {
        Group ring = ruum.ring();
        root.getChildren().add(ring);
        label.setText("Ruum " + ruum.getNumber() + "\n\n" + ruum.getInfo() + "\n\n" + "Mahutavus: " + ruum.getMahutavus());
        label.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}