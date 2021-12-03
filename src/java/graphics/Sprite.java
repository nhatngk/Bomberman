package graphics;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Sprite {
    public static Image load(String filePath) {
        Image image = new FxImage(filePath, 16).getImage();
        WritableImage writableImage = new WritableImage(48, 48);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader();
        for (int x = -1; x < 48; ++x) {
            for (int y = -1; y < 48; ++y) {
                if (x >= 0 && y >= 0) {
                    if (pixelReader.getArgb(x / 3, y / 3) == 0xffff00ff) {
                        pixelWriter.setColor(x / 3, y / 3, Color.TRANSPARENT);
                    } else {
                        pixelWriter.setColor(x, y, pixelReader.getColor(x / 3, y / 3));
                    }
                }
            }
        }
        return writableImage;
    }
    /**
     * Board sprites.
     */

    public static final Image grass = load("/sprites/grass.png");
    public static final Image brick = load("/sprites/brick.png");
    public static final Image wall = load("/sprites/wall.png");
    public static final Image portal = load("/sprites/portal.png");

    /**
     * Player sprites.
     */
    public static final Image player_up = load("/sprites/player_up.png");
    public static final Image player_down = load("/sprites/player_down.png");
    public static final Image player_left = load("/sprites/player_left.png");
    public static final Image player_right  = load("/sprites/player_right.png");

    public static final Image player_up_1 = load("/sprites/player_up_1.png");
    public static final Image player_up_2 = load("/sprites/player_up_2.png");

    public static final Image player_down_1 = load("/sprites/player_down_1.png");
    public static final Image player_down_2 = load("/sprites/player_down_2.png");

    public static final Image player_left_1 = load("/sprites/player_left_1.png");
    public static final Image player_left_2 = load("/sprites/player_left_2.png");

    public static final Image player_right_1 = load("/sprites/player_right_1.png");
    public static final Image player_right_2 = load("/sprites/player_right_2.png");

    public static final Image player_dead1 = load("/sprites/player_dead1.png");
    public static final Image player_dead2 = load("/sprites/player_dead2.png");
    public static final Image player_dead3 = load("/sprites/player_dead3.png");

    /**
     * Enemy sprites.
     */
    //Balloom
    public static final Image balloom_left1 = load("/sprites/balloom_left1.png");
    public static final Image balloom_left2 = load("/sprites/balloom_left2.png");
    public static final Image balloom_left3 = load("/sprites/balloom_left3.png");

    public static final Image balloom_right1 = load("/sprites/balloom_right1.png");
    public static final Image balloom_right2 = load("/sprites/balloom_right2.png");
    public static final Image balloom_right3 = load("/sprites/balloom_right3.png");

    //Oneal
    public static final Image oneal_left1 = load("/sprites/oneal_left1.png");
    public static final Image oneal_left2 = load("/sprites/oneal_left2.png");
    public static final Image oneal_left3 = load("/sprites/oneal_left3.png");

    public static final Image oneal_right1 = load("/sprites/oneal_right1.png");
    public static final Image oneal_right2 = load("/sprites/oneal_right2.png");
    public static final Image oneal_right3 = load("/sprites/oneal_right3.png");


    //Doll
    public static final Image doll_left1 = load("/sprites/doll_left1.png");
    public static final Image doll_left2 = load("/sprites/doll_left2.png");
    public static final Image doll_left3 = load("/sprites/doll_left3.png");

    public static final Image doll_right1 = load("/sprites/doll_right1.png");
    public static final Image doll_right2 = load("/sprites/doll_right2.png");
    public static final Image doll_right3 = load("/sprites/doll_right3.png");

    //Minvo
    public static final Image minvo_left1 = load("/sprites/minvo_left1.png");
    public static final Image minvo_left2 = load("/sprites/minvo_left2.png");
    public static final Image minvo_left3 = load("/sprites/minvo_left3.png");

    public static final Image minvo_right1 = load("/sprites/minvo_right1.png");
    public static final Image minvo_right2 = load("/sprites/minvo_right2.png");
    public static final Image minvo_right3 = load("/sprites/minvo_right3.png");

    //Kondoria
    public static final Image kondoria_left1 = load("/sprites/kondoria_left1.png");
    public static final Image kondoria_left2 = load("/sprites/kondoria_left2.png");
    public static final Image kondoria_left3 = load("/sprites/kondoria_left3.png");

    public static final Image kondoria_right1 = load("/sprites/kondoria_right1.png");
    public static final Image kondoria_right2 = load("/sprites/kondoria_right2.png");
    public static final Image kondoria_right3 = load("/sprites/kondoria_right3.png");

    //All
    public static final Image mob_dead1 = load("/sprites/mob_dead1.png");
    public static final Image mob_dead2 = load("/sprites/mob_dead2.png");
    public static final Image mob_dead3 = load("/sprites/mob_dead3.png");

    /**
     * Bomb sprites.
     */
    public static final Image bomb = load("/sprites/bomb.png");
    public static final Image bomb_1 = load("/sprites/bomb_1.png");
    public static final Image bomb_2 = load("/sprites/bomb_2.png");

    /**
     * FlameSegment Sprites.
     */
    public static final Image bomb_exploded = load("/sprites/bomb_exploded.png");
    public static final Image bomb_exploded1 = load("/sprites/bomb_exploded1.png");
    public static final Image bomb_exploded2 = load("/sprites/bomb_exploded2.png");

    public static final Image explosion_vertical = load("/sprites/explosion_vertical.png");
    public static final Image explosion_vertical1 = load("/sprites/explosion_vertical1.png");
    public static final Image explosion_vertical2 = load("/sprites/explosion_vertical2.png");

    public static final Image explosion_horizontal = load("/sprites/explosion_horizontal.png");
    public static final Image explosion_horizontal1 = load("/sprites/explosion_horizontal1.png");
    public static final Image explosion_horizontal2 = load("/sprites/explosion_horizontal2.png");

    public static final Image explosion_horizontal_left_last  = load("/sprites/explosion_horizontal_left_last.png");
    public static final Image explosion_horizontal_left_last1= load("/sprites/explosion_horizontal_left_last.png");
    public static final Image explosion_horizontal_left_last2 = load("/sprites/explosion_horizontal_left_last2.png");

    public static final Image explosion_horizontal_right_last = load("/sprites/explosion_horizontal_right_last.png");
    public static final Image explosion_horizontal_right_last1 = load("/sprites/explosion_horizontal_right_last1.png");
    public static final Image explosion_horizontal_right_last2 = load("/sprites/explosion_horizontal_right_last2.png");

    public static final Image explosion_vertical_top_last = load("/sprites/explosion_vertical_top_last.png");
    public static final Image explosion_vertical_top_last1 = load("/sprites/explosion_vertical_top_last1.png");
    public static final Image explosion_vertical_top_last2 = load("/sprites/explosion_vertical_top_last2.png");

    public static final Image explosion_vertical_down_last = load("/sprites/explosion_vertical_down_last.png");
    public static final Image explosion_vertical_down_last1 = load("/sprites/explosion_vertical_down_last1.png");
    public static final Image explosion_vertical_down_last2 = load("/sprites/explosion_vertical_down_last2.png");

    /**
     * Brick FlameSegment.
     */
    public static final Image brick_exploded = load("/sprites/brick_exploded.png");
    public static final Image brick_exploded1 = load("/sprites/brick_exploded1.png");
    public static final Image brick_exploded2 = load("/sprites/brick_exploded2.png");

    /**
     * Powerups.
     */
    // Thêm bom
    public static final Image powerup_bombs = load("/sprites/powerup_bombs.png");
    // Bom nổ rộng hơn
    public static final Image powerup_flames = load("/sprites/powerup_flames.png");
    // Player tăng tốc
    public static final Image powerup_speed = load("/sprites/powerup_speed.png");
    // Đi xuyên wall
    public static final Image powerup_wallpass = load("/sprites/powerup_wallpass.png");
    // Bom đặt vô hạn time, nổ khi kích hoạt hoặc có bom nổ trong phạm vi
    public static final Image powerup_detonator = load("/sprites/powerup_bombpass.png");
    // Bỏ qua flame
    public static final Image powerup_flamepass = load("/sprites/powerup_flamepass.png");
    // Đi xuyên qua bom
    public static final Image powerup_bombpass = load("/sprites/powerup_bombpass.png");


    public static Image getMoveSprite(Image normal, Image x1, Image x2, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if (calc < diff) {
            return normal;
        }
        if (calc < diff*2) {
            return x1;
        }
        return x2;
    }

    public static Image getMoveSprite(Image x1, Image x2, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? x1 : x2;
    }

}