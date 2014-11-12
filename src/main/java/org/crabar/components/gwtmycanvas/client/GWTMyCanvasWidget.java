package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import org.crabar.components.gwtmycanvas.client.gameObjects.Ball;
import org.crabar.components.gwtmycanvas.client.gameObjects.Platform;

/**
 * Created by Crabar on 08.11.2014.
 */
public class GWTMyCanvasWidget extends Composite {

    public static final String CLASSNAME = "mycomponent";
    private static final int FRAMERATE = 30;
    private Canvas canvas;
    private Platform platform;
    private Ball ball;

    public GWTMyCanvasWidget() {
        canvas = Canvas.createIfSupported();
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (platform != null) {
                    platform.setCenterX(event.getX());
                }
            }
        });
        initWidget(canvas);
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent resizeEvent) {
                resizeCanvas(resizeEvent.getWidth(), resizeEvent.getHeight());
            }
        });
        initGameTimer();
        resizeCanvas(Window.getClientWidth(), Window.getClientHeight());
        setStyleName(CLASSNAME);
        platform = createPlatform();
        ball = createBall();
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

        timer.scheduleRepeating(1000 / FRAMERATE);
    }

    private void drawCanvas() {
        canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        if (platform != null)
            platform.draw(canvas.getContext2d());
        if (ball != null)
            ball.draw(canvas.getContext2d());
    }

    private Platform createPlatform() {
        Platform platform = new Platform();
        platform.setY(Window.getClientHeight() - platform.getHeight());
        return platform;
    }
}
