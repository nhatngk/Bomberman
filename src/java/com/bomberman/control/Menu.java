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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Menu {
    static int top;
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
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("top.txt")));
            String data = reader.readLine();
            StringTokenizer tokens = new StringTokenizer(data);
            top = Integer.parseInt(tokens.nextToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        startL.autosize();
        startL.setLayoutX(340);
        startL.setLayoutY(470);

        continueL = new Label("CONTINUE");
        continueL.setTextFill(Color.web("#ffffff"));
        continueL.setFont(font);
        continueL.autosize();
        continueL.setLayoutX(300);
        continueL.setLayoutY(530);

        topScore = new Label("TOP " + top);
        topScore.setTextFill(Color.web("#ffffff"));
        topScore.setFont(font);
        topScore.autosize();
        topScore.setLayoutX(340);
        topScore.setLayoutY(590);

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
            Map.loadMapFile("Level0.txt");
            Map.initScene();
            Sound.BGM.stop();
            scene = Map.getScene();
            stage_.setScene(scene);
        });
    }
}
