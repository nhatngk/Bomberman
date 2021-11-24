package com.bomberman.entities.stacticEntities;

import com.bomberman.constants.Const;
import com.bomberman.entities.AnimatedEntity;
import com.bomberman.control.Map;
import com.bomberman.entities.RectBox;
import graphics.Sprite;
import javafx.scene.image.Image;

 public class Brick extends AnimatedEntity {

    private int removeTime = 30;

    private boolean exploded = false;

    public Brick(int x, int y, Image brick) {
        super(x, y, brick);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public Brick(int x, int y) {
        super(x, y, Sprite.brick);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
    }

    public void playAnimation() {
        if (!exploded) {
            image = Sprite.brick;
        } else {
            image = Sprite.getMoveSprite(Sprite.brick_exploded
                    , Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 30);
        }
    }

    public void setExploded() {
        exploded = true;
    }

    public void update() {
        if (exploded) {
            if (removeTime > 0) {
                removeTime--;
            } else {
                remove();
                if ('*' == Map.mapMatrix[y_pos / Const.BLOCK_SIZE][x_pos / Const.BLOCK_SIZE]) {
                    Map.mapMatrix[y_pos / Const.BLOCK_SIZE][x_pos / Const.BLOCK_SIZE] = ' ';
                }
            }
        }
        animation();
        playAnimation();
    }
}


