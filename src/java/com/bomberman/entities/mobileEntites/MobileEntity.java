package com.bomberman.entities.mobileEntites;

import com.bomberman.constants.Const;
import com.bomberman.constants.Direction;
import com.bomberman.entities.AnimatedEntity;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.stacticEntities.Brick;
import com.bomberman.entities.stacticEntities.Wall;
import javafx.scene.image.Image;


public abstract class MobileEntity extends AnimatedEntity {
    protected Direction currentDirection;
    protected boolean isMoving;
    protected boolean alive;
    protected boolean ableToPassWall = false;
    protected boolean ableToPassBrick = false;
    protected int passAwayTime = 30;
    protected int aBigStep = Const.BLOCK_SIZE;
    protected int speed;


    public MobileEntity(int x, int y, Image image) {
        super(x, y, image);
        isMoving = false;
        currentDirection = Direction.DOWN;
    }


    public void move(int steps, Direction direction) {
        if (alive) {
            if (steps == 0 || direction == null) {
                isMoving = false;
            } else {
                switch (direction) {
                    case UP:
                        if (checkFriendlyCollisions(x_pos, y_pos - steps)) {
                            y_pos -= steps;
                            currentDirection = Direction.UP;
                            isMoving = true;
                        } else {
                            isMoving = false;
                            image = this.getUpImage();
                        }
                        break;
                    case DOWN:
                        if (checkFriendlyCollisions(x_pos, y_pos + steps)) {
                            y_pos += steps;
                            currentDirection = Direction.DOWN;
                            isMoving = true;
                        } else {
                            isMoving = false;
                            image = this.getDownImage();
                        }
                        break;
                    case LEFT:
                        if (checkFriendlyCollisions(x_pos - steps, y_pos)) {
                            x_pos -= steps;
                            currentDirection = Direction.LEFT;
                            isMoving = true;
                        } else {
                            isMoving = false;
                            image = this.getLeftImage();
                        }
                        break;
                    case RIGHT:
                        if (checkFriendlyCollisions(x_pos + steps, y_pos)) {
                            x_pos += steps;
                            currentDirection = Direction.RIGHT;
                            isMoving = true;
                        } else {
                            isMoving = false;
                            image = this.getRightImage();
                        }
                        break;
                }
            }
        }
    }

    public boolean movableSteps(int steps, Direction direction) {
        switch (direction) {
            case UP:
                return checkFriendlyCollisions(x_pos, y_pos - steps);
            case DOWN:
                return checkFriendlyCollisions(x_pos, y_pos + steps);
            case LEFT:
                return checkFriendlyCollisions(x_pos - steps, y_pos);
            case RIGHT:
                return checkFriendlyCollisions(x_pos + steps, y_pos);
            default:
                return false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isAbleToPassWall() {
        return ableToPassWall;
    }

    public boolean isAbleToPassBrick() {
        return ableToPassBrick;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean checkFriendlyCollisions(int x, int y) {
        boundedBox.setPosition(x, y);
        for (Entity entity : Map.getBoardLayer()) {
            if (entity instanceof Wall && isColliding(entity) && !ableToPassWall) {
                boundedBox.setPosition(x_pos, y_pos);
                return false;
            }
        }
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Brick && isColliding(entity) && !ableToPassBrick) {
                boundedBox.setPosition(x_pos, y_pos);
                return false;
            }
        }
        boundedBox.setPosition(x_pos, y_pos);
        return true;
    }

    public abstract Image getUpImage();

    public abstract Image getDownImage();

    public abstract Image getRightImage();

    public abstract Image getLeftImage();
}
