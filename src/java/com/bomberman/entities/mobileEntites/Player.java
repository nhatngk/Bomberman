package com.bomberman.entities.mobileEntites;

import com.bomberman.constants.Const;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.RectBox;
import com.bomberman.entities.bomb.Bomb;
import com.bomberman.entities.bomb.BombExplosion;
import com.bomberman.control.Controller;
import com.bomberman.control.Sound;
import com.bomberman.entities.mobileEntites.enemies.Enemy;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Player extends MobileEntity {
    private static Player player;

    private int bombCount = 1;
    private int placedBombs = 0;
    private int bombRadius = 1;

    private boolean ableToPassFlame = false;
    private boolean ableToPassBomb = false;
    private boolean speedBoosted = false;

    private int timePassFlame = 500;
    private int timePassBomb = 500;
    private int timeSpeedBoosted = 500;
    private int timePassWall = 500;
    private int timePassBrick = 500;

    private int x_init, y_init;

    Controller controller;

    private final List<Bomb> bombList = new ArrayList<>();

    public Player(int x, int y, Image player) {
        super(x, y, player);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE - 10, Const.SCALED_SIZE - 2);
        alive = true;
        controller = new Controller();
        x_init = x;
        y_init = y;
        speed = 2;
    }

    public Player(int x, int y) {
        super(x, y, Sprite.player_right);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE - 10, Const.SCALED_SIZE - 2);
        alive = true;
        controller = new Controller();
        x_init = x;
        y_init = y;
        speed = 2;
    }

    public static void resetPlayer() {
        player = new Player(0, 0);
    }

    public static Player setPlayer(int x, int y) {
        if (player == null) {
            player = new Player(x, y);
        } else {
            player.setPosition(x, y);
        }
        return player;
    }

    public static Player setPlayerPlus(int x, int y, int bombCount, int bombRadius, int speed,
                                boolean ableToPassFlame, boolean ableToPassBomb,
                                boolean ableToPassWall, boolean ableToPassBrick,
                                boolean alive) {
        player = new Player(x, y);
        player.ableToPassBomb = ableToPassBomb;
        player.ableToPassFlame = ableToPassFlame;
        player.bombRadius = bombRadius;
        if (speed == 4) {
            player.speedBoosted = true;
        }
        player.speed = speed;
        player.bombCount = bombCount;
        player.ableToPassBrick = ableToPassBrick;
        player.ableToPassWall = ableToPassWall;
        player.alive = alive;
        return player;
    }

    public static Player getPlayer() {
        return player;
    }

    public void update() {
        animation();
        checkEnemyCollision();
        recountBombs();
        playAnimation();
        try {
            controller.playerMovementHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
        x_node = x_pos / Const.BLOCK_SIZE;
        y_node = y_pos / Const.BLOCK_SIZE;
    }

    public void playAnimation() {
        if (alive) {
            switch(currentDirection) {
                case UP:
                    if (isMoving) {
                        image = Sprite.getMoveSprite(Sprite.player_up
                                , Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                    }
                    break;
                case DOWN:
                    if (isMoving) {
                        image = Sprite.getMoveSprite(Sprite.player_down
                                , Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                    }
                    break;
                case RIGHT:
                    if (isMoving) {
                        image = Sprite.getMoveSprite(Sprite.player_right
                                , Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                    }
                    break;
                case LEFT:
                    if (isMoving) {
                        image = Sprite.getMoveSprite(Sprite.player_left
                                , Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                    }
                    break;
            }
        } else {
            image = Sprite.getMoveSprite(Sprite.player_dead1
                    , Sprite.player_dead2, Sprite.player_dead3, animate, 30);
        }
    }

    public void setPosition(int x, int y) {
        x_pos = x;
        y_pos = y;
        boundedBox.setPosition(x, y);
    }

    @Override
    public boolean checkFriendlyCollisions(int x, int y) {
        boundedBox.setPosition(x, y);
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Bomb && isColliding(entity)
                    && !((Bomb) entity).allowToPass() && !ableToPassBomb) {
                boundedBox.setPosition(x_pos, y_pos);
                return false;
            }
        }
        if (x < Const.BLOCK_SIZE || x > Map.CANVAS_WIDTH - Const.BLOCK_SIZE) {
            boundedBox.setPosition(x_pos, y_pos);
            return false;
        }
        if (y < Const.BLOCK_SIZE || y > Map.CANVAS_HEIGHT - Const.BLOCK_SIZE) {
            boundedBox.setPosition(x_pos, y_pos);
            return false;
        }
        return super.checkFriendlyCollisions(x, y);
    }

    public void checkEnemyCollision() {
        if (alive == true) {
            for (Entity entity : Map.getEnemyLayer()) {
                if (entity instanceof Enemy && isColliding(entity)) {
                    Sound.Die.play(false);
                    alive = false;
                    remove();
                    break;
                }
            }
            for (Entity entity : Map.getTopLayer()) {
                if (entity instanceof BombExplosion
                        && isColliding(entity) && !ableToPassFlame) {
                    alive = false;
                    remove();
                    break;
                }
                if (entity instanceof Bomb && isColliding(entity)
                        && ((Bomb) entity).isExploded() && !ableToPassFlame) {
                    alive = false;
                    remove();
                    break;
                }
            }
        }
    }


    public void placeBomb() {
        int x_bomb = ((x_pos + Const.SCALED_SIZE / 2) / Const.SCALED_SIZE) * Const.SCALED_SIZE;
        int y_bomb = ((y_pos + Const.SCALED_SIZE / 2) / Const.SCALED_SIZE) * Const.SCALED_SIZE;
        boolean placeable = true;
        for (Entity bomb : bombList) {
            if (bomb.getX_pos() == x_bomb && bomb.getY_pos() == y_bomb) {
                placeable = false;
                break;
            }
        }
        if (placedBombs < bombCount && placeable && alive) {
            Bomb bomb = new Bomb(x_bomb, y_bomb);
            Map.getTopLayer().add(bomb);
            bombList.add(bomb);
            Map.mapMatrix[y_bomb / Const.BLOCK_SIZE][x_bomb / Const.BLOCK_SIZE] = '*';
            new Sound("/sound/place_bomb.wav").play(false);
        }
    }

    private void recountBombs() {
        placedBombs = bombList.size();
        for (int i = 0; i < bombList.size(); i++) {
            if (bombList.get(i).isRemoved()) {
                bombList.remove(i);
                --i;
            }
        }
    }

    public void setAbleToPassBomb() {
        ableToPassBomb = true;
    }

    public void setAbleToPassWall() {
        ableToPassBrick = true;
    }

    public void setAbleToPassFlame() {
        ableToPassFlame = true;
    }

    public void setSpeedBooster() {
        speed = 4;
        speedBoosted = true;
    }

    public int getSpeed() {
        return speed;
    }


    public int getBombCount() {
        return bombCount;
    }

    public boolean isAbleToPassFlame() {
        return ableToPassFlame;
    }

    public boolean isAbleToPassBomb() {
        return ableToPassBomb;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void increaseFlame() {
        bombRadius++;
    }

    public void increaseBombs() {
        bombCount++;
    }

    public int getRemainBombs() {
        return bombCount - placedBombs;
    }

    public void setBombCount() {
        bombList.clear();
    }

    public Image getUpImage() {
        return Sprite.player_up;
    }

    public Image getDownImage() {
        return Sprite.player_down;
    }

    public Image getRightImage() {
        return Sprite.player_right;
    }

    public Image getLeftImage() {
        return Sprite.player_left;
    }

    public int getX_node() {
        return x_node;
    }

    public int getY_node() {
        return y_node;
    }
}
