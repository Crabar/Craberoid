package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;


/**
 * Created by ypoliakov on 11.11.2014.
 */
public class Platform extends Rectangle implements IDynamicObject {

    public Platform() {
        setWidth(80);
        setHeight(20);
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
        setX(centerX - getWidth() / 2);
    }

    private int centerX;

    public int getCenterX() {
        return centerX;
    }

    @Override
    public void draw(Context2d context2d) {
        context2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void update(double elapsedTime) {

    }
}
