package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
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
    private Canvas backBufferCanvas;
    private Platform platform;
    private Ball ball;
    private ArrayList<IDynamicObject> gameObjects;
    private boolean needDraw = true;

    public GWTMyCanvasWidget() {
        canvas = Canvas.createIfSupported();
        backBufferCanvas = Canvas.createIfSupported();
        initHandlers();
        initWidget(canvas);
        initGameTimer();
        resizeCanvas(canvas, Window.getClientWidth(), Window.getClientHeight());
        resizeCanvas(backBufferCanvas, Window.getClientWidth(), Window.getClientHeight());
        setStyleName(CLASSNAME);
        gameObjects = new ArrayList<IDynamicObject>();
        platform = createPlatform();
        ball = createBall();
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
                resizeCanvas(canvas, resizeEvent.getWidth(), resizeEvent.getHeight());
                resizeCanvas(backBufferCanvas, resizeEvent.getWidth(), resizeEvent.getHeight());
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
        ball.setCenterY(Window.getClientHeight() / 2);
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

    private void drawBuffer(Context2d bufferContext) {
        canvas.getContext2d().drawImage(bufferContext.getCanvas(), 0, 0);
    }

    private void drawFPS(Context2d canvas, double elapsedTime) {
        canvas.setFont("20pt serif");
        canvas.fillText("FPS: " + String.valueOf(Math.round(1000 / elapsedTime)), 50, 50);
    }

    private void drawCanvas(double elapsedTime) {
//        backBufferCanvas.getContext2d().clearRect(0, 0, backBufferCanvas.getCoordinateSpaceWidth(), backBufferCanvas.getCoordinateSpaceHeight());
        canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        for (IDynamicObject gameObject : gameObjects) {
            gameObject.update(elapsedTime);
        }
        for (IDynamicObject gameObject : gameObjects) {
            gameObject.draw(canvas.getContext2d());
        }
        ball.collideWithWalls(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        ball.collideWithObject(platform, false);
//        drawBuffer(canvas.getContext2d());
    }

    private Platform createPlatform() {
        Platform platform = new Platform();
        platform.setY(Window.getClientHeight() - platform.getHeight());
        return platform;
    }
}
