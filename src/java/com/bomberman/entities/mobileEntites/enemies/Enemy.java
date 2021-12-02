package com.bomberman.entities.mobileEntites.enemies;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.RectBox;
import com.bomberman.entities.bomb.Bomb;
import com.bomberman.entities.bomb.BombExplosion;
import com.bomberman.entities.mobileEntites.MobileEntity;
import javafx.scene.image.Image;


public abstract class Enemy extends MobileEntity {
    protected int score;
    protected Moving moving;

    public Enemy(int x, int y, Image image) {
        super(x, y, image);
        boundedBox = new RectBox(x, y, Const.BLOCK_SIZE, Const.BLOCK_SIZE);
        alive = true;
    }

    @Override
    public void update() {
        animation();
        checkBombCollision();
        if (!alive) {
            if (passAwayTime > 0) {
                passAwayTime--;
            } else {
                remove();
            }
        }
        playAnimation();
        try {
            enemySmartMoving();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkFriendlyCollisions(int x, int y) {
        boundedBox.setPosition(x, y);
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Bomb && isColliding(entity)) {
                boundedBox.setPosition(x_pos, y_pos);
                return false;
            }
        }
        return super.checkFriendlyCollisions(x, y);
    }

    public void checkBombCollision() {
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof BombExplosion && isColliding(entity)) {
                alive = false;
                break;
            }
        }
    }

    protected void enemySmartMoving() {
        if (aBigStep > 0 && movableSteps(speed, currentDirection)) {
            move(speed, currentDirection);
            aBigStep -= speed;
        } else {
            aBigStep = Const.BLOCK_SIZE;
            switch (currentDirection) {
                case UP:
                    y_node -= 1;
                    break;
                case DOWN:
                    y_node += 1;
                    break;
                case LEFT:
                    x_node -= 1;
                    break;
                case RIGHT:
                    x_node += 1;
                    break;
            }
            currentDirection = moving.movingDirection(Map.mapMatrix, x_node, y_node);
        }
    }

    public int getScore() {
        return score;
    }
}
