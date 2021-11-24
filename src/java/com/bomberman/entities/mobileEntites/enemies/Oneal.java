package com.bomberman.entities.mobileEntites.enemies;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image oneal) {
        super(x, y, oneal);
        score = 200;
        speed = 2;
        moving = new Moving(Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }

    public Oneal(int x, int y) {
        super(x, y, Sprite.oneal_right1);
        score = 200;
        speed = 2;
        moving = new Moving(Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }

    public void playAnimation() {
        if (alive) {
            switch (currentDirection) {
                case UP:
                case RIGHT:
                    image = Sprite.getMoveSprite(Sprite.oneal_right1
                            , Sprite.oneal_right2, Sprite.oneal_right3, animate, 30);
                    break;
                case DOWN:
                case LEFT:
                    image = Sprite.getMoveSprite(Sprite.oneal_left1
                            , Sprite.oneal_left2, Sprite.oneal_left3, animate, 30);
                    break;
            }
        }
        else {
            image = Sprite.getMoveSprite(Sprite.mob_dead1
                    , Sprite.mob_dead2, Sprite.mob_dead3, animate, 30);
        }

    }



    public Image getUpImage() {
        return Sprite.oneal_right1;
    }

    public Image getDownImage() {
        return Sprite.oneal_left1;
    }

    public Image getRightImage() {
        return Sprite.oneal_right1;
    }

    public Image getLeftImage() {
        return Sprite.oneal_left1;
    }
}
