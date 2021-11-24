package com.bomberman.entities.bomb;

import com.bomberman.constants.Const;
import com.bomberman.constants.Direction;
import com.bomberman.entities.AnimatedEntity;
import com.bomberman.entities.Entity;
import com.bomberman.control.Map;
import com.bomberman.entities.RectBox;
import com.bomberman.control.Sound;
import com.bomberman.entities.mobileEntites.Player;
import com.bomberman.entities.stacticEntities.Brick;
import graphics.Sprite;
import javafx.scene.image.Image;

public class Bomb extends AnimatedEntity {
    private int countDownTime = 120;

    private int removeTime = 30;

    private final int explosionTime = 50;

    private Player player = Player.getPlayer();

    private boolean allowToPass = true;

    private boolean exploded = false;

    private ExplosionDirection[] explosions;

    private BombExplosion explosion;

    public Bomb(int x, int y, Image bomb) {
        super(x, y, bomb);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE, Const.SCALED_SIZE);
    }

    public Bomb(int x, int y) {
        super(x, y, Sprite.bomb);
        boundedBox = new RectBox(x, y, Const.SCALED_SIZE, Const.SCALED_SIZE);
    }

    public void update() {
        if (countDownTime > 0) {
            countDownTime--;
        } else {
            if (!exploded) {
                setExplosions();
                exploded = true;
                new Sound("/sound/explosion.wav").play(false);
            }
            if (removeTime > 0) {
                removeTime--;
            } else {
                Map.mapMatrix[y_node][x_node] = ' ';
                remove();
            }
        }
        animation();
        playAnimation();
        setAllowToPass();
    }


    public void playAnimation() {
        if (exploded) {
            image = Sprite.getMoveSprite(Sprite.bomb_exploded
                    , Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 30);
        } else {
            image = Sprite.getMoveSprite(Sprite.bomb
                    , Sprite.bomb_1, Sprite.bomb_2, animate, 50);
        }
    }


    public void setExplosions() {

        explosions = new ExplosionDirection[4];
        Entity entity = Map.getStaticEntityAt(x_pos, y_pos);
        if (entity instanceof Brick) {
            ((Brick) entity).setExploded();
        }
        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new ExplosionDirection(x_pos, y_pos, Direction.dir[i], Player.getPlayer().getBombRadius());
            for (int j = 0; j < explosions[i].getExplosions().length; j++) {
                Map.getTopLayer().add(explosions[i].getExplosions()[j]);
            }
        }
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setAllowToPass() {
        if (!isColliding(Player.getPlayer())) {
            allowToPass = false;
        }
    }

    public boolean allowToPass() {
        return allowToPass;
    }
}
