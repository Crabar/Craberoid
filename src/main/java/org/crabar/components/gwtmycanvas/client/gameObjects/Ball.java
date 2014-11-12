package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import org.crabar.components.gwtmycanvas.client.resources.CraberoidClientBundle;

/**
 * Created by ypoliakov on 12.11.2014.
 */
public class Ball implements IDrawable {

    public Ball() {
        final Image img = new Image(CraberoidClientBundle.INSTANCE.ball());
        ballImage = ImageElement.as(img.getElement());
//        img.addLoadHandler(new LoadHandler() {
//            @Override
//            public void onLoad(LoadEvent loadEvent) {
//
//            }
//        });


    }

    ImageElement ballImage;

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    int centerX;
    int centerY;

    public int getRadius() {
        return radius;
    }

    int radius = 25;

    @Override
    public void draw(Context2d context2d) {
//        logger.log(Level.SEVERE, String.valueOf(getCenterX() - getRadius()));
//        context2d.fillRect(getCenterX() - getRadius(), getCenterY() - getRadius(), getRadius() * 2, getRadius() * 2);
        context2d.drawImage(ballImage, (double) (getCenterX() - getRadius()), (double) (getCenterY() - getRadius()), (double) (getRadius() * 2), (double) (getRadius() * 2));
    }
}
