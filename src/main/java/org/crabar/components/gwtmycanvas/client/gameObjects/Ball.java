package org.crabar.components.gwtmycanvas.client.gameObjects;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.touch.client.*;
import com.google.gwt.user.client.ui.Image;
import org.crabar.components.gwtmycanvas.client.resources.CraberoidClientBundle;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by ypoliakov on 12.11.2014.
 */
public class Ball implements IDynamicObject {

    ImageElement ballImage;
    int centerX;
    int centerY;
    int previousCenterX;
    int previousCenterY;

    int radius = 20;
    double velocity[] = {0, 0}; // px per sec

    public Ball() {
        final Image img = new Image(CraberoidClientBundle.INSTANCE.ball());
        ballImage = ImageElement.as(img.getElement());
    }

    public Logger getLogger() {
        return Logger.getLogger("Ball.java");
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

    public void collideWithWalls(int left, int top, int right, int bottom) {
        if (getCenterX() - getRadius() < left) {
            velocity[0] = - velocity[0];
        }
        if (getCenterX() + getRadius() > right) {
            double yIntersect = Math.sqrt(Math.pow(getRadius(), 2) - Math.pow(getCenterX() - right, 2)) + getCenterY();
            velocity[0] = - velocity[0];
        } else if (getCenterY() - getRadius() < top || (getCenterY() + getRadius() > bottom)) {
            velocity[1] = - velocity[1];
        }
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2 , 2) + Math.pow(y1 - y2, 2));
    }

    public void collideWithObject(Rectangle object, Boolean smartPhysic) {
        Boolean intersectLeftSide = isBallIntersectSide(object.getX(), object.getY(), object.getX(), object.getY() + object.getHeight());
        Boolean intersectRightSide = isBallIntersectSide(object.getX() + object.getWidth(), object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight());
        Boolean intersectTopSide = isBallIntersectSide(object.getX(), object.getY(), object.getX() + object.getWidth(), object.getY());
        Boolean intersectBottomSide = isBallIntersectSide(object.getX(), object.getY() + object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight());

        if ((intersectLeftSide || intersectRightSide) && !(intersectBottomSide || intersectTopSide)) {
            velocity[0] = - velocity[0];
        }
        if ((intersectBottomSide || intersectTopSide) && !(intersectLeftSide || intersectRightSide)) {
            velocity[1] = - velocity[1];
        }
        if (intersectLeftSide && intersectTopSide) {
            velocity[0] = - Math.abs(velocity[0]);
            velocity[1] = - Math.abs(velocity[1]);
        }
        if (intersectLeftSide && intersectBottomSide) {
            velocity[0] = - Math.abs(velocity[0]);
            velocity[1] = Math.abs(velocity[1]);
        }
        if (intersectRightSide && intersectTopSide) {
            velocity[0] = Math.abs(velocity[0]);
            velocity[1] = - Math.abs(velocity[1]);
        }
        if (intersectRightSide && intersectBottomSide) {
            velocity[0] = Math.abs(velocity[0]);
            velocity[1] = Math.abs(velocity[1]);
        }
    }

    public Boolean collideWithObject2(Rectangle object, Boolean smartPhysic) {
        ArrayList<SideVO> sides = new ArrayList<SideVO>();
        sides.add(new SideVO(object.getX(), object.getY(), object.getX(), object.getY() + object.getHeight(), "left"));
        sides.add(new SideVO(object.getX() + object.getWidth(), object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight(), "right"));
        sides.add(new SideVO(object.getX(), object.getY(), object.getX() + object.getWidth(), object.getY(), "top"));
        sides.add(new SideVO(object.getX(), object.getY() + object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight(), "bottom"));

        Boolean isIntersect = false;

        for (SideVO side : sides) {
            isIntersect = isIntersect || isBallIntersectSide(side.x1, side.y1, side.x2, side.y2);
        }

        if (!isIntersect) {
            return false;
        }

        SideVO wall = findIntersectionWall(sides);

        switch (wall.name) {
            case "top":
            case "bottom": {
                velocity[1] = -velocity[1];
            }
            case "left":
            case "right": {
                velocity[0] = -velocity[0];
            }
        }

        return true;

//        Boolean intersectLeftSide = isBallIntersectSide(object.getX(), object.getY(), object.getX(), object.getY() + object.getHeight());
//        Boolean intersectRightSide = isBallIntersectSide(object.getX() + object.getWidth(), object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight());
//        Boolean intersectTopSide = isBallIntersectSide(object.getX(), object.getY(), object.getX() + object.getWidth(), object.getY());
//        Boolean intersectBottomSide = isBallIntersectSide(object.getX(), object.getY() + object.getY(), object.getX() + object.getWidth(), object.getY() + object.getHeight());

    }

    private SideVO findIntersectionWall(ArrayList<SideVO> sides) {
        int sideIntersectionCounter = 1;
        SideVO wall = sides.get(0);

        while (sideIntersectionCounter > 0) {
            sideIntersectionCounter = 0;
            for (SideVO side : sides) {
                if (isBallIntersectSide(side.x1, side.y1, side.x2, side.y2)) {
                    sideIntersectionCounter++;
                    wall = side;
                }
            }

            centerX -= velocity[0] / 200;
            centerY -= velocity[1] / 200;
        }

        return wall;
    }

    private Boolean isBallIntersectSide(int x1, int y1, int x2, int y2) {
        int oX1 = x1 - (int) getCenterX();
        int oX2 = x2 - (int) getCenterX();
        int oY1 = y1 - (int) getCenterY();
        int oY2 = y2 - (int) getCenterY();

        int dx = oX2 - oX1;
        int dy = oY2 - oY1;

        double a = dx * dx + dy * dy;
        double b = 2 * (oX1 * dx + oY1 * dy);
        double c = oX1 * oX1 + oY1 * oY1 - getRadius() * getRadius();

        if (-b < 0) {
            return c < 0;
        }
        if (-b < 2 * a) {
            return (4 * a * c - b * b) < 0;
        }

        return (a + b + c < 0);
    }

//    private Point calcIntersectionOffset() {
//
//    }


    @Override
    public void draw(Context2d context2d) {
//        context2d.fillRect((getCenterX() - getRadius()), (getCenterY() - getRadius()), (getRadius() * 2), (getRadius() * 2));
        context2d.drawImage(ballImage, (getCenterX() - getRadius()), (getCenterY() - getRadius()), (getRadius() * 2), (getRadius() * 2));
    }

    private Rectangle boundBox = new Rectangle();

    @Override
    public void update(double elapsedTime) {
        double velocityFactor = 1000 / elapsedTime;
        previousCenterX = centerX;
        previousCenterY = centerY;
        centerX += Math.round(velocity[0] / velocityFactor);
        centerY += Math.round(velocity[1] / velocityFactor);
//        getLogger().log(Level.SEVERE, "x: " + String.valueOf(centerX) + " y: " + String.valueOf(centerY) + " elapsed time:" + String.valueOf(elapsedTime));
    }

    public void setVelocity(int horizontalVelocity, int verticalVelocity) {
        velocity[0] = horizontalVelocity;
        velocity[1] = verticalVelocity;
    }

}
