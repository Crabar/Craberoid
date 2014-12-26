package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.crabar.components.gwtmycanvas.client.gameObjects.Ball;
import org.crabar.components.gwtmycanvas.client.gameObjects.Brick;
import org.crabar.components.gwtmycanvas.client.gameObjects.IDynamicObject;
import org.crabar.components.gwtmycanvas.client.gameObjects.Platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Crabar on 08.11.2014.
 */
public class GWTMyCanvasWidget extends Composite {

    public static final String CLASSNAME = "mycomponent";
    private static final int FRAME_RATE = 30;
    private Canvas canvas;
    private Canvas levelCanvas;
    private AbsolutePanel panel;
    private Platform platform;
    private Ball ball;
    private ArrayList<IDynamicObject> gameObjects;
    private ArrayList<Brick> bricks;
    private boolean needDraw = true;

    public GWTMyCanvasWidget() {
        panel = new AbsolutePanel();
        canvas = Canvas.createIfSupported();
        levelCanvas = Canvas.createIfSupported();
        initWidget(panel);
        panel.add(levelCanvas, 0, 0);
        panel.add(canvas, 0, 0);
        levelCanvas.getElement().getStyle().setZIndex(0);
        canvas.getElement().getStyle().setZIndex(1);
        resizePanel(Window.getClientWidth(), Window.getClientHeight());
        resizeCanvas(canvas, Window.getClientWidth(), Window.getClientHeight());
        resizeCanvas(levelCanvas, Window.getClientWidth(), Window.getClientHeight());
        initHandlers();
        initGameTimer();
        setStyleName(CLASSNAME);
        gameObjects = new ArrayList<IDynamicObject>();
        platform = createPlatform();
        ball = createBall();
        gameObjects.add(platform);
        gameObjects.add(ball);
        generateLevel();
    }

    public Logger getLogger() {
        return Logger.getLogger("GWTMyCanvasWidget.java");
    }

    private void resizePanel(int width, int height) {
        panel.setWidth(width + "px");
        panel.setHeight(height + "px");
    }

    private void generateLevel() {
        SimpleLevelGenerator levelGenerator = new SimpleLevelGenerator();
        bricks = levelGenerator.generateLevel(100, 100, Window.getClientWidth() - 200, Window.getClientHeight() - 400);
        getLogger().log(Level.SEVERE, String.valueOf(bricks.size()));
        gameObjects.addAll(bricks);
        getLogger().log(Level.SEVERE, String.valueOf(gameObjects.size()));
        levelCanvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());

        for (Brick brick : bricks) {
            brick.draw(levelCanvas.getContext2d());
        }
    }

    private void initHandlers() {
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (platform != null) {
                    platform.setCenterX(event.getX());
                }
            }
        });
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent resizeEvent) {
                resizePanel(Window.getClientWidth(), Window.getClientHeight());
                resizeCanvas(canvas, Window.getClientWidth(), Window.getClientHeight());
                resizeCanvas(levelCanvas, Window.getClientWidth(), Window.getClientHeight());
            }
        });
        canvas.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_SPACE) {
                    ball.setVelocity(200, 200);
                }
                if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    needDraw = false;
                }
            }
        });
    }


    private Ball createBall() {
        Ball ball = new Ball();
        ball.setCenterX(Window.getClientWidth() / 2);
        ball.setCenterY(Window.getClientHeight() - 100);
        return ball;
    }

    private void resizeCanvas(Canvas canvas, int width, int height) {
        canvas.setWidth(width + "px");
        canvas.setCoordinateSpaceWidth(width);
        canvas.setHeight(height + "px");
        canvas.setCoordinateSpaceHeight(height);
    }

    private double previousFrameTimestamp = 0;

    private void initGameTimer() {
        AnimationScheduler.get().requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
            @Override
            public void execute(double timestamp) {
                double elapsedTime = timestamp - previousFrameTimestamp;
                if (needDraw) {
                    drawCanvas(elapsedTime);
                }
                drawFPS(canvas.getContext2d(), elapsedTime);
                previousFrameTimestamp = timestamp;
                AnimationScheduler.get().requestAnimationFrame(this);
            }
        });
    }


    private void drawFPS(Context2d canvas, double elapsedTime) {
        canvas.setFont("20pt serif");
        canvas.fillText("FPS: " + String.valueOf(Math.round(1000 / elapsedTime)), 50, 50);
    }

    private void drawCanvas(double elapsedTime) {
//        levelCanvas.getContext2d().clearRect(0, 0, levelCanvas.getCoordinateSpaceWidth(), levelCanvas.getCoordinateSpaceHeight());
        canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        for (IDynamicObject gameObject : gameObjects) {
            if (!(gameObject instanceof Brick)) {
                gameObject.draw(canvas.getContext2d());
            }
        }
        for (IDynamicObject gameObject : gameObjects) {
            if (!(gameObject instanceof Brick)) {
                gameObject.update(elapsedTime);
            }

        }
        ball.collideWithWalls(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        ball.collideWithObject(platform, false);

        Boolean intersection;
        for (Brick brick : bricks) {
//            intersection = ball.collideWithObject2(brick, false);
//            if (intersection) {
//                break;
//            }
        }
    }

    private Platform createPlatform() {
        Platform platform = new Platform();
        platform.setY(Window.getClientHeight() - platform.getHeight());
        return platform;
    }
}
