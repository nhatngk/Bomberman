package com.bomberman.entities.bomb;

import com.bomberman.constants.Const;
import com.bomberman.constants.Direction;
import com.bomberman.entities.AnimatedEntity;
import com.bomberman.entities.RectBox;
import graphics.Sprite;
import javafx.scene.image.Image;

public class BombExplosion extends AnimatedEntity {
    private int countDownTime = 10;

    private int removeTime = 60;

    private Direction explosionDir;

    private boolean isLast;

    public BombExplosion(int x, int y, Image image) {
        super(x, y, image);
    }

    public BombExplosion(int x, int y, Direction dir, boolean last) {
        super(x, y, null);
        boundedBox = new RectBox(x + Const.SCALED_SIZE / 8, y + Const.SCALED_SIZE / 8, Const.SCALED_SIZE * 7 / 8, Const.SCALED_SIZE * 7 / 8);
        explosionDir = dir;
        isLast = last;
    }

    public void update() {
        if (removeTime > 0) {
            removeTime--;
        } else {
            remove();
        }
        animation();
        playAnimation();
    }

    public void playAnimation() {
        switch (explosionDir) {
            case UP:
                if (isLast) {
                    image = Sprite.getMoveSprite(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, animate, 60);
                } else {
                    image = Sprite.getMoveSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, 60);
                }
                break;
            case DOWN:
                if (isLast) {
                    image = Sprite.getMoveSprite(Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, animate, 60);
                } else {
                    image = Sprite.getMoveSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, 60);
                }
                break;
            case LEFT:
                if (isLast) {
                    image = Sprite.getMoveSprite(Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, animate, 60);
                } else {
                    image = Sprite.getMoveSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, 60);
                }
                break;
            case RIGHT:
                if (isLast) {
                    image = Sprite.getMoveSprite(Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, animate, 60);
                } else {
                    image = Sprite.getMoveSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, 60);
                }
                break;
        }
    }
}
