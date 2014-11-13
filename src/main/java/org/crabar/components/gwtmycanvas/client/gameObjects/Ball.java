package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import org.crabar.components.gwtmycanvas.client.resources.CraberoidClientBundle;

/**
 * Created by ypoliakov on 12.11.2014.
 */
public class Ball implements IDynamicObject {

    ImageElement ballImage;
    int centerX;
    int centerY;
    int radius = 25;
    double velocity[] = {0, 0}; // px per sec
    private double velocityFactor;
    private int gameFrameRate = 1;

    public Ball() {
        final Image img = new Image(CraberoidClientBundle.INSTANCE.ball());
        ballImage = ImageElement.as(img.getElement());
    }

    public int getGameFrameRate() {
        return gameFrameRate;
    }

    public void setGameFrameRate(int gameFrameRate) {
        this.gameFrameRate = gameFrameRate;
        this.velocityFactor = 1000 / getGameFrameRate();
    }

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

    public int getRadius() {
        return radius;
    }

    @Override
    public void draw(Context2d context2d) {
        context2d.drawImage(ballImage, (double) (getCenterX() - getRadius()), (double) (getCenterY() - getRadius()), (double) (getRadius() * 2), (double) (getRadius() * 2));
    }

    @Override
    public void update() {
        centerX += Math.round(velocity[0] / velocityFactor);
        centerY += Math.round(velocity[1] / velocityFactor);
    }

    public void setVelocity(int horizontalVelocity, int verticalVelocity) {
        velocity[0] = horizontalVelocity;
        velocity[1] = verticalVelocity;
    }

}
