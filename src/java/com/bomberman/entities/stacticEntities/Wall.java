package com.bomberman.entities.stacticEntities;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.entities.RectBox;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Wall extends Entity{
    public Wall(int x, int y, Image wall) {
        super(x, y, wall);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public Wall(int x, int y) {
        super(x, y, Sprite.wall);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public void update() {

    }
}