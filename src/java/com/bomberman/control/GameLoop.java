package com.bomberman.control;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.entities.mobileEntites.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;


public class GameLoop {
    private static int cd1;
    private static int cd2;
    public static void start(GraphicsContext graphicsContext) {
        cd1 = 50;
        cd2 = 50;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                graphicsContext.clearRect(0, 0
                        , Map.mapWidth * Const.SCALED_SIZE
                        , Map.mapHeight * Const.SCALED_SIZE);
                updateGame();
                renderGame(graphicsContext);
                Map.exportLevel();
                if (cd1 == 0) {
                    stop();
                    cd1 = 50;
                    Map.gameOver("GAME OVER");
                }
                if (!Player.getPlayer().isAlive()) {
                    cd1--;
                }
                if (Map.isPassLevel) {
                    cd2--;
                }
                if (cd2 == 0) {
                    stop();
                    Map.isPassLevel = false;
                    cd2 = 50;
                    Map.tranfer();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), ev -> {
                        System.out.println("test");
                        Map.nextLevel();
                        start();
                    }));
                    timeline.play();
                }
                if (Map.win == true) {
                    stop();
                    Map.win = false;
                    Map.gameOver("YOU WIN!!");
                }
            }
        };
        timer.start();
    }

    private static void updateGame() {
        Map.bombs.setText("Bomb: " + Player.getPlayer().getRemainBombs());
        Map.score.setText("Score: " + Map.gameScore);
        Map.enemies.setText("Left: " + Map.getEnemyLayer().size());

        for (int i = 0; i < Map.getMidLayer().size(); i++) {
            Map.getMidLayer().get(i).update();
        }
        for (int i = 0; i < Map.getTopLayer().size(); i++) {
            Map.getTopLayer().get(i).update();
        }
        for (int i = 0; i < Map.getEnemyLayer().size(); i++) {
            Map.getEnemyLayer().get(i).update();
        }

        Player.getPlayer().update();
        Map.setCameraView();
        Map.removeEntity();
    }

    private static void renderGame(GraphicsContext graphicsContext) {

        for (Entity entity : Map.getBoardLayer()) {
            entity.render(graphicsContext);
        }

        for (Entity entity : Map.getMidLayer()) {
            entity.render(graphicsContext);
        }
        for (Entity entity : Map.getTopLayer()) {
            entity.render(graphicsContext);
        }
        for (Entity entity : Map.getEnemyLayer()) {
            entity.render(graphicsContext);
        }
        Player.getPlayer().render(graphicsContext);
    }
}
