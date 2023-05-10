package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Majaplaan extends Application {
    String highlightStyle = "-fx-fill: green; -fx-opacity: 0.3;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image image = new Image("korrus1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(1000);
        imageView.setFitWidth(1000);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(imageView);

        Ruum ruum1 = new Ruum("1037", 16.0, 87.0, 131.0, 155.0);
        Ruum ruum2 = new Ruum("1020", 285.0, 135.0, 115.0, 105.0);

        ArrayList<Ruum> Ruumid = new ArrayList<>();
        Ruumid.add(ruum1);
        Ruumid.add(ruum2);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for a classroom");
        searchBar.setOnKeyReleased(event -> {
            for (Ruum ruum : Ruumid) {
                if (ruum.getNimi().equalsIgnoreCase(searchBar.getText())) {
                    Rectangle highlight = new Rectangle(ruum.getX(), ruum.getY(), ruum.getWidth(), ruum.getHeight());
                    highlight.setStyle(highlightStyle);
                    anchorPane.getChildren().add(highlight);
                    Text roomName = new Text(ruum.getX() + ruum.getWidth()/2 - ruum.getNimi().length()*3, ruum.getY() + ruum.getHeight()/2, ruum.getNimi());
                    roomName.setFill(Color.BLACK);
                    Group highlightGroup = new Group();
                    highlightGroup.getChildren().addAll(highlight, roomName);

                    anchorPane.getChildren().add(highlightGroup);
                }else {
                    anchorPane.getChildren().removeIf(node -> node instanceof Group);
                }
            }
        });

        imageView.setOnMouseMoved(event -> {
            for (Ruum ruum : Ruumid) {
                if (ruum.contains(event.getX(), event.getY())) {
                    Rectangle highlightRectangle = ruum.highlightRuum();
                    highlightRectangle.setStyle(highlightStyle);
                    anchorPane.getChildren().add(highlightRectangle);

                    Text roomName = new Text(ruum.getX() + ruum.getWidth()/2 - ruum.getNimi().length()*3, ruum.getY() + ruum.getHeight()/2, ruum.getNimi());
                    roomName.setFill(Color.BLACK);
                    anchorPane.getChildren().add(roomName);

                    highlightRectangle.setOnMouseExited(e -> {
                        anchorPane.getChildren().removeAll(highlightRectangle, roomName);
                    });
                }
            }
        });

        Scene scene = new Scene(new VBox(searchBar, anchorPane));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
