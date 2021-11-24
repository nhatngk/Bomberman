package com.bomberman.entities.mobileEntites.enemies;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image kondoria) {
        super(x, y, kondoria);
        score = 1000;
        ableToPassBrick = true;
        speed = 2;
        moving = new Moving(Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }

    public Kondoria(int x, int y) {
        super(x, y, Sprite.kondoria_right1);
        score = 1000;
        ableToPassBrick = true;
        speed = 2;
        moving = new Moving (Moving.Level.MEDIUM,
                ableToPassBrick, ableToPassWall);
    }


    public void playAnimation() {
        if (alive) {
            switch (currentDirection) {
                case UP:
                case RIGHT:
                    image = Sprite.getMoveSprite(Sprite.kondoria_right1
                            , Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 60);
                    break;
                case DOWN:
                case LEFT:
                    image = Sprite.getMoveSprite(Sprite.kondoria_left1
                            , Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 60);
                    break;
            }
        }
        else {
            image = Sprite.getMoveSprite(Sprite.mob_dead1
                    , Sprite.mob_dead2, Sprite.mob_dead3, animate, 30);
        }

    }

    public Image getUpImage() {
        return Sprite.kondoria_right1;
    }

    public Image getDownImage() {
        return Sprite.kondoria_left1;
    }

    public Image getRightImage() {
        return Sprite.kondoria_right1;
    }

    public Image getLeftImage() {
        return Sprite.kondoria_left1;
    }

}
