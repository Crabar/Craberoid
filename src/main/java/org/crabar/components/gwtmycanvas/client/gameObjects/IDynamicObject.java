package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Created by ypoliakov on 13.11.2014.
 */
public interface IDynamicObject {
    void update();
    void draw(Context2d context2d);
}
