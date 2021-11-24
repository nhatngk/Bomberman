package com.bomberman.entities.stacticEntities;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.entities.RectBox;
import javafx.scene.image.Image;

public abstract class Item extends Entity {

    public Item(int x, int y, Image powerup) {
        super(x, y, powerup);
        boundedBox = new RectBox(x + Const.SCALED_SIZE / 8,
                y + Const.SCALED_SIZE / 8,
                Const.SCALED_SIZE * 7 / 8,
                Const.SCALED_SIZE * 7 / 8);
    }

    public abstract void checkPlayerCollision();

    public void update() {
        checkPlayerCollision();
    }
}
