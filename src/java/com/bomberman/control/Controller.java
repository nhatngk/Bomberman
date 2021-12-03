package com.bomberman.control;

import com.bomberman.constants.Direction;
import com.bomberman.entities.mobileEntites.Player;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static List<KeyCode> playerController = new ArrayList<>();
    public static void attachEventHandler(Scene scene) {
        playerController.clear();
        scene.setOnKeyPressed(keyEvent ->{
            KeyCode keyCode = keyEvent.getCode();
            if (! playerController.contains(keyCode)) {
                playerController.add(keyCode);
            }
        });
        scene.setOnKeyReleased(keyEvent ->{
            KeyCode keyCode = keyEvent.getCode();
            playerController.remove(keyCode);
        });
    }

    public void playerMovementHandler() {
        Player player = Player.getPlayer();

        if (playerController.contains(KeyCode.UP) || playerController.contains(KeyCode.W)) {
            player.move(player.getSpeed(), Direction.UP);
        }

        if (playerController.contains(KeyCode.DOWN) || playerController.contains(KeyCode.S)) {
            player.move(player.getSpeed(), Direction.DOWN);
        }

        if (playerController.contains(KeyCode.LEFT) || playerController.contains(KeyCode.A)) {
            player.move(player.getSpeed(), Direction.LEFT);
        }

        if (playerController.contains(KeyCode.RIGHT) || playerController.contains(KeyCode.D)) {
            player.move(player.getSpeed(), Direction.RIGHT);
        }

        if (!playerController.contains(KeyCode.UP) && !playerController.contains(KeyCode.W)
                && !playerController.contains(KeyCode.DOWN) && !playerController.contains(KeyCode.S)
                && !playerController.contains(KeyCode.LEFT) && !playerController.contains(KeyCode.A)
                && !playerController.contains(KeyCode.RIGHT) && !playerController.contains(KeyCode.D)) {
            player.move(0, Direction.DOWN);
        }

        if (playerController.contains(KeyCode.SPACE)) {
            player.placeBomb();
        }
    }

}

