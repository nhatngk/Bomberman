package com.bomberman.entities.stacticEntities;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.entities.RectBox;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Grass extends Entity {

    public Grass(int x, int y, Image grass) {
        super(x, y, grass);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public Grass(int x, int y) {
        super(x, y, Sprite.grass);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public void update() {

    }
}