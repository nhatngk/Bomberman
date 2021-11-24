package com.bomberman.entities.stacticEntities.items;

import com.bomberman.constants.Const;
import com.bomberman.control.Map;
import com.bomberman.control.Sound;
import com.bomberman.entities.mobileEntites.Player;
import com.bomberman.entities.stacticEntities.Item;
import graphics.Sprite;
import javafx.scene.image.Image;

public class FlamePassItem extends Item {
    public FlamePassItem(int x, int y, Image powerup) {
        super(x, y, powerup);
    }

    public FlamePassItem(int x, int y) {
        super(x, y, Sprite.powerup_flamepass);
    }

    public void checkPlayerCollision() {
        if (isColliding(Player.getPlayer())) {
            Player.getPlayer().setAbleToPassFlame();
            Map.mapMatrix[y_pos / Const.BLOCK_SIZE][x_pos / Const.BLOCK_SIZE] = ' ';
            new Sound("/sound/power_up.wav").play(false);
            remove();
        }
    }
}
