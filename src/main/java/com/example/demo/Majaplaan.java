package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Majaplaan extends Application {

    StackPane stackPane;

    private double mouseX;
    private double mouseY;
    private Label roomLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ImageView imageView = new ImageView(new Image("korrus2.jpg"));
        imageView.setPreserveRatio(true);

        roomLabel = new Label();
        roomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField searchField = new TextField();
        searchField.setPromptText("Otsi ruume...");
        searchField.setOnMouseClicked(event -> searchField.requestFocus());
        searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                searchField.setStyle("-fx-border-color: blue;");
            } else {
                searchField.setStyle("");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                clearHighlight();
            } else {
                highlightRoomByName(newValue);
            }
        });

        BorderPane root2 = new BorderPane();

        imageView.fitWidthProperty().bind(root2.widthProperty());
        imageView.fitHeightProperty().bind(root2.heightProperty());

        stackPane = new StackPane(imageView, roomLabel);

        root2.setCenter(stackPane);

        Scene scene = new Scene(root2);

        Label coordinatesLabel = new Label("Mouse coordinates: ");

        root2.setBottom(coordinatesLabel);

        imageView.setOnMouseMoved(e -> {
            double mouseX = e.getX();
            double mouseY = e.getY();
            double imageWidth = imageView.getImage().getWidth();
            double imageHeight = imageView.getImage().getHeight();
            double relativeX = mouseX / imageWidth * 1000;
            double relativeY = mouseY / imageHeight * 1000;
            coordinatesLabel.setText("Relative coordinates: (" + relativeX + ", " + relativeY + ")");
        });
        stackPane.setOnMouseMoved(this::handleMouseMove);

        stage.setScene(scene);
        stage.setTitle("Majaplaan");
        stage.show();
    }

    private void handleMouseMove(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();

        if (isOverRoomA(getRoomAArea(), mouseX, mouseY)) {
            String ROOM_A = "Room A";
            roomLabel.setText(ROOM_A);
            highlightArea(getRoomAArea(), "Room A");
        } else if (isOverRoomB(getRoomBArea(), mouseX, mouseY)) {
            String ROOM_B = "Room B";
            roomLabel.setText(ROOM_B);
            highlightArea(getRoomBArea(), "Room B");
        } else if (isOverRoomC(getRoomCArea(), mouseX, mouseY)) {
            String ROOM_C = "Room C";
            roomLabel.setText(ROOM_C);
            highlightArea(getRoomCArea(), "Room C");
        } else {
            roomLabel.setText("");
        }
    }
    private boolean isOverRoomA (double[] roomA, double x, double y){
        double relativeX1 = mouseX * 1000/roomA[0];
        double relativeY1 = mouseX * 1000/(roomA[3] + roomA[0]);
        double relativeX2 = mouseX * 1000/roomA[1];
        double relativeY2 = mouseX * 1000/(roomA[3] + roomA[1]);


        return x >= relativeX1 && x <= relativeX2 && y >= relativeY1 && y <= relativeY2;
    }
    private boolean isOverRoomB (double[] roomB, double x, double y){
        return x >= roomB[0] && x <= roomB[3] + roomB[0] && y >= roomB[1] && y <= roomB[3] + roomB[1];
    }
    private boolean isOverRoomC (double[] roomC,double x, double y){
        return x >= roomC[0] && x <= roomC[3] + roomC[0] && y >= roomC[1] && y <= roomC[3] + roomC[1];
    }

    private double[] getRoomAArea() {
        double roomAX1 = 50;
        double roomAY1 = 5;
        double roomAX2 = 70;
        double roomAY2 = 20;

        double width = roomAX2 - roomAX1;
        double height = roomAY2 - roomAY1;

        return new double[] { roomAX1, roomAY1, width, height };
    }

    private double[] getRoomBArea() {
        double roomBX1 = 300;
        double roomBY1 = 100;
        double roomBX2 = 400;
        double roomBY2 = 200;

        double width = roomBX2 - roomBX1;
        double height = roomBY2 - roomBY1;

        return new double[] { roomBX1, roomBY1, width, height };
    }

    private double[] getRoomCArea() {
        double roomCX1 = 500;
        double roomCY1 = 100;
        double roomCX2 = 600;
        double roomCY2 = 200;

        double width = roomCX2 - roomCX1;
        double height = roomCY2 - roomCY1;

        return new double[] { roomCX1, roomCY1, width, height };
    }

    private void highlightRoomByName(String roomName) {
        switch (roomName) {
            case "Room A" -> highlightArea(getRoomAArea(), "Room A");
            case "Room B" -> highlightArea(getRoomBArea(), "Room B");
            case "Room C" -> highlightArea(getRoomCArea(), "Room C");
            default -> clearHighlight();
        }
    }
    private void clearHighlight() {
        stackPane.getChildren().removeIf(node -> node instanceof Rectangle);
        roomLabel.setText("");
    }
    private void highlightArea(double[] area, String roomName) {

        Rectangle highlight = new Rectangle(area[0], area[1], area[2], area[3]);
        highlight.setFill(Color.rgb(255, 0, 0, 0.2));
        clearHighlight();
        stackPane.getChildren().add(highlight);
        roomLabel.setText(roomName);
    }
}




