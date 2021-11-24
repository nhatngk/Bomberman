package com.bomberman.entities.stacticEntities;

import com.bomberman.control.Map;
import com.bomberman.control.Sound;
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
        if (isColliding(Player.getPlayer()) && Map.getEnemyLayer().size() == 0) {
            new Sound("/sound/power_up.wav").play(false);
            Map.nextLevel();
        }
    }

    public void update() {
        checkPlayerCollision();
    }
}
