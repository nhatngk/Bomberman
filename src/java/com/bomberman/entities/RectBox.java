package com.bomberman.entities;

import javafx.geometry.Rectangle2D;

public class RectBox {
    int x;
    int y;
    int width;
    int height;
    Rectangle2D boundedBox;

    public RectBox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        boundedBox = new Rectangle2D(x, y, width, height);
    }

    public Rectangle2D getBoundedBox() {
        return boundedBox;
    }

    public boolean checkCollision(RectBox other) {
        return other.getBoundedBox().intersects(boundedBox);
    }

    public void setPosition(int x, int y) {
        boundedBox = new Rectangle2D(x, y, width, height);
    }
}
