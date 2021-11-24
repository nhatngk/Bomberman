package com.bomberman.entities.mobileEntites.enemies;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Doll extends Enemy {
    public Doll(int x, int y, Image doll) {
        super(x, y, doll);
        score = 400;
        speed = 2;
        moving = new Moving(Moving.Level.LOW,
                ableToPassBrick, ableToPassWall);
    }

    public Doll(int x, int y) {
        super(x, y, Sprite.doll_right1);
        score = 400;
        speed = 2;
        moving = new Moving(Moving.Level.LOW,
                ableToPassBrick, ableToPassWall);
    }


    public void playAnimation() {
        if (alive) {
            switch (currentDirection) {
                case UP:
                case RIGHT:
                    image = Sprite.getMoveSprite(Sprite.doll_right1
                            , Sprite.doll_right2, Sprite.doll_right3, animate, 20);
                    break;
                case DOWN:
                case LEFT:
                    image = Sprite.getMoveSprite(Sprite.doll_left1
                            , Sprite.doll_left2, Sprite.doll_left3, animate, 20);
                    break;
            }
        }
        else {
            image = Sprite.getMoveSprite(Sprite.mob_dead1
                    , Sprite.mob_dead2, Sprite.mob_dead3, animate, 30);
        }
    }

    public Image getUpImage() {
        return Sprite.doll_right1;
    }

    public Image getDownImage() {
        return Sprite.doll_left1;
    }

    public Image getRightImage() {
        return Sprite.doll_right1;
    }

    public Image getLeftImage() {
        return Sprite.doll_left1;
    }

}
