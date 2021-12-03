package com.bomberman.control;

import com.bomberman.Main;
import com.bomberman.constants.Const;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu {
    static Scene scene;
    public static AnchorPane anchorPane;
    public static ImageView imageView = new ImageView();
    public static Label startL;
    public static Label continueL;
    public static Label topScore;
    private static Stage stage_;

    public static Scene menuScene(Stage stage) {
        Sound.BGM.play(true);
        initLabel();
        stage_ = stage;

        Image image = new Image(Main.class.getResourceAsStream("/sprites/Menu.png"));
        imageView.setImage(image);
        imageView.setLayoutX((Const.SCENE_WIDTH - image.getWidth()) / 2);

        anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(imageView, startL, continueL, topScore);
        anchorPane.styleProperty().set("-fx-background-color: BLACK");

        scene = new Scene(anchorPane, Const.SCENE_WIDTH, Const.SCENE_HEIGHT);
        return scene;
    }

    private static void initLabel() {
        Font font = Font.loadFont(Main.class.getResourceAsStream("/Font/joystix monospace.ttf"), 30);

        startL = new Label("START");
        startL.setTextFill(Color.web("#ffffff"));
        startL.setFont(font);
        startL.setPrefWidth(150);
        startL.setPrefHeight(25);
        startL.setLayoutX(300);
        startL.setLayoutY(460);

        continueL = new Label("CONTINUE");
        continueL.setTextFill(Color.web("#ffffff"));
        continueL.setFont(font);
        continueL.autosize();
        continueL.setLayoutX(300);
        continueL.setLayoutY(520);

        topScore = new Label("HIGH SCORE");
        topScore.setTextFill(Color.web("#ffffff"));
        topScore.setFont(font);
        topScore.autosize();
        topScore.setLayoutX(300);
        topScore.setLayoutY(580);

        //handle event
        //start
        startL.setOnMouseEntered(MouseEvent ->{
            startL.setTextFill(Color.web("#ff3422"));
        });
        startL.setOnMouseExited(MouseEvent ->{
            startL.setTextFill(Color.web("#ffffff"));
        });
        startL.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Map.initScene();
            scene = Map.getScene();
            stage_.setScene(scene);
        });
        //continue
        continueL.setOnMouseEntered(MouseEvent ->{
            continueL.setTextFill(Color.web("#ff3422"));
        });
        continueL.setOnMouseExited(MouseEvent ->{
            continueL.setTextFill(Color.web("#ffffff"));
        });
        continueL.setOnMouseClicked(MouseEvent ->{
            Sound.BGM.stop();
            Map.loadMapFile("/Level0.txt");
            Map.initScene();
            scene = Map.getScene();
            stage_.setScene(scene);
        });
        //hight score
        topScore.setOnMouseEntered(MouseEvent ->{
            topScore.setTextFill(Color.web("#ff3422"));
        });
        topScore.setOnMouseExited(MouseEvent ->{
            topScore.setTextFill(Color.web("#ffffff"));
        });
        topScore.setOnMouseClicked(MouseEvent ->{

        });
    }
}
