package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Created by ypoliakov on 11.11.2014.
 */
public class Platform implements IDrawable {
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    private int centerX;
    private int height = 20;

    public int getWidth() {
        return width;
    }

    private int width = 80;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public int getCenterX() {
        return centerX;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void draw(Context2d context2d) {
        context2d.fillRect(getCenterX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }
}
