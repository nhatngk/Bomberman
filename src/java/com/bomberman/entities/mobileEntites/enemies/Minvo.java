package com.bomberman.entities.mobileEntites.enemies;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Minvo extends Enemy {
    public Minvo(int x, int y, Image minvo) {
        super(x, y, minvo);
        score = 800;
        speed = 3;
        moving = new Moving(Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }

    public Minvo(int x, int y) {
        super(x, y, Sprite.minvo_right1);
        score = 800;
        speed = 3;
        moving = new Moving(Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }


    public void playAnimation() {
        if (alive) {
            switch (currentDirection) {
                case UP:
                case RIGHT:
                    image = Sprite.getMoveSprite(Sprite.minvo_right1
                            , Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
                    break;
                case DOWN:
                case LEFT:
                    image = Sprite.getMoveSprite(Sprite.minvo_left1
                            , Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
                    break;
            }
        }
        else {
            image = Sprite.getMoveSprite(Sprite.mob_dead1
                    , Sprite.mob_dead2, Sprite.mob_dead3, animate, 30);
        }
    }

    public Image getUpImage() {
        return Sprite.minvo_right1;
    }

    public Image getDownImage() {
        return Sprite.minvo_left1;
    }

    public Image getRightImage() {
        return Sprite.minvo_right1;
    }

    public Image getLeftImage() {
        return Sprite.minvo_left1;
    }

}
