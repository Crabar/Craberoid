package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import org.crabar.components.gwtmycanvas.client.gameObjects.Ball;
import org.crabar.components.gwtmycanvas.client.gameObjects.IDynamicObject;
import org.crabar.components.gwtmycanvas.client.gameObjects.Platform;

import java.util.ArrayList;

/**
 * Created by Crabar on 08.11.2014.
 */
public class GWTMyCanvasWidget extends Composite {

    public static final String CLASSNAME = "mycomponent";
    private static final int FRAME_RATE = 30;
    private Canvas canvas;
    private Platform platform;
    private Ball ball;
    private ArrayList<IDynamicObject> gameObjects;

    public GWTMyCanvasWidget() {
        canvas = Canvas.createIfSupported();
        initHandlers();
        initWidget(canvas);
        initGameTimer();
        resizeCanvas(Window.getClientWidth(), Window.getClientHeight());
        setStyleName(CLASSNAME);
        gameObjects = new ArrayList<IDynamicObject>();
        platform = createPlatform();
        ball = createBall();
        ball.setGameFrameRate(FRAME_RATE);
        gameObjects.add(platform);
        gameObjects.add(ball);
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
                resizeCanvas(resizeEvent.getWidth(), resizeEvent.getHeight());
            }
        });
        canvas.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_SPACE) {
                    ball.setVelocity(200, 200);
                }
            }
        });
    }


    private Ball createBall() {
        Ball ball = new Ball();
        ball.setCenterX(Window.getClientWidth() / 2);
        ball.setCenterY(Window.getClientHeight() / 2);
        return ball;
    }

    private void resizeCanvas(int width, int height) {
        canvas.setWidth(width + "px");
        canvas.setCoordinateSpaceWidth(width);
        canvas.setHeight(height + "px");
        canvas.setCoordinateSpaceHeight(height);
    }

    private void initGameTimer() {
        Timer timer = new Timer() {
            @Override
            public void run() {
                drawCanvas();
            }
        };

        timer.scheduleRepeating(1000 / FRAME_RATE);
    }

    private void drawCanvas() {
        canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        for (IDynamicObject gameObject : gameObjects) {
            gameObject.update();
            gameObject.draw(canvas.getContext2d());
        }
    }

    private Platform createPlatform() {
        Platform platform = new Platform();
        platform.setY(Window.getClientHeight() - platform.getHeight());
        return platform;
    }
}
