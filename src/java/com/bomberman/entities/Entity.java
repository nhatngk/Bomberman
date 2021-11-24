package com.bomberman.entities;

import com.bomberman.constants.Const;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public abstract class Entity {
    protected int x_pos;
    protected int y_pos;
    protected int x_node;
    protected int y_node;
    protected boolean removed;
    protected Image image;
    protected RectBox boundedBox;

    public Entity(int x, int y, Image image) {

        x_pos = x;
        y_pos = y;

        x_node = x / Const.BLOCK_SIZE;
        y_node = y / Const.BLOCK_SIZE;

        this.image = image;
        removed = false;
    }

    public abstract void update();

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, x_pos, y_pos);
    }

    public void remove() {
        removed = true;
    }
    public boolean isRemoved() {
        return removed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public RectBox getBoundedBox() {
        return boundedBox;
    }

    public boolean isColliding(Entity other) {
        RectBox otherBox = other.getBoundedBox();
        return boundedBox.checkCollision(otherBox);
    }

    public int getX_pos() {
        return x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }
}