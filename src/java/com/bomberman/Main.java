package com.bomberman;

import com.bomberman.constants.Const;
import com.bomberman.control.Menu;
import graphics.Sprite;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends javafx.application.Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        stage.getIcons().add(Sprite.bomb);
        stage.setTitle(Const.GAME_NAME + " " + Const.GAME_VER);
        Scene scene = Menu.menuScene(stage);
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setFullScreen(false);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}