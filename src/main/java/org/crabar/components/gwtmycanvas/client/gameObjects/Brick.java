package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Created by ypoliakov on 14.11.2014.
 */
public class Brick extends Rectangle implements IDynamicObject {
    public Brick(int x, int y) {
        setBounds(x, y, 70, 30);
    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void draw(Context2d context2d) {
        context2d.setFillStyle("0xff0000");
        context2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}
