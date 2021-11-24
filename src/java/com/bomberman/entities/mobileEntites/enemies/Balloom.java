package com.bomberman.entities.mobileEntites.enemies;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Balloom extends Enemy {
    public Balloom(int x, int y, Image ballom) {
        super(x, y, ballom);
        score = 100;
        speed = 1;
        moving = new Moving(Moving.Level.LOW,
                ableToPassBrick, ableToPassWall);
    }

    public Balloom(int x, int y) {
        super(x, y, Sprite.balloom_right1);
        score = 100;
        speed = 1;
        moving = new Moving(Moving.Level.LOW,
                ableToPassBrick, ableToPassWall);
    }

    public void playAnimation() {
        if (alive) {
            switch (currentDirection) {
                case UP:
                case RIGHT:
                    image = Sprite.getMoveSprite(Sprite.balloom_right1
                            , Sprite.balloom_right2, Sprite.balloom_right3, animate, 30);
                    break;
                case DOWN:
                case LEFT:
                    image = Sprite.getMoveSprite(Sprite.balloom_left1
                            , Sprite.balloom_left2, Sprite.balloom_left3, animate, 30);
                    break;
            }
        }
        else {
            image = Sprite.getMoveSprite(Sprite.mob_dead1
                    , Sprite.mob_dead2, Sprite.mob_dead3, animate, 30);
        }
    }

    public Image getUpImage() {
        return Sprite.balloom_right1;
    }

    public Image getDownImage() {
        return Sprite.balloom_left1;
    }

    public Image getRightImage() {
        return Sprite.balloom_right1;
    }

    public Image getLeftImage() {
        return Sprite.balloom_left1;
    }

}
