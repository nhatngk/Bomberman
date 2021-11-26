package com.bomberman;

import com.bomberman.constants.Const;
import com.bomberman.control.Map;
import com.bomberman.control.Sound;
import graphics.Sprite;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(Sprite.bomb);
        stage.setTitle(Const.GAME_NAME + " " + Const.GAME_VER);
        Map.initScene();
        Scene scene = Map.getScene();
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setFullScreen(false);
        Sound.BGM.play(true);
    }

    public static void main(String[] args) {
        launch();
    }
}