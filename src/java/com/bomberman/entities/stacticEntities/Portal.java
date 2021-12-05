package com.bomberman.entities.stacticEntities;

import com.bomberman.control.Map;
import com.bomberman.entities.mobileEntites.Player;
import graphics.Sprite;
import javafx.scene.image.Image;



public class Portal extends Item {
    public Portal(int x, int y, Image portal) {
        super(x, y, portal);
    }

    public Portal(int x, int y) {
        super(x, y, Sprite.portal);
    }

    public void checkPlayerCollision() {
        if (isColliding(Player.getPlayer()) || Map.getEnemyLayer().size() == 0) {
            if (Map.currentLevel < 5) {
                Map.isPassLevel = true;
            } else {
                Map.win = true;
            }
        }
    }

    public void update() {
        checkPlayerCollision();
    }
}
