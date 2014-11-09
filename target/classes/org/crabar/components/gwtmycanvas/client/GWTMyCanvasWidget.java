package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

/**
 * Created by Crabar on 08.11.2014.
 */
public class GWTMyCanvasWidget extends Composite {

    public static final String CLASSNAME = "mycomponent";

    public GWTMyCanvasWidget() {
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setHeight("100%");
        _canvas = Canvas.createIfSupported();
        _canvas.setWidth("100%");
        _canvas.setHeight("100%");
        panel.add(_canvas);
        _canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                textX = event.getX();
            }
        });
        initWidget(panel);
        initGameTimer();
        setStyleName(CLASSNAME);

    }

    private Canvas _canvas;

    private void initGameTimer() {
        Timer timer = new Timer() {
            @Override
            public void run() {
//                textX += 1;
                drawCanvas();
            }
        };

        timer.scheduleRepeating(50);
    }

    private int textX;

    private void drawCanvas() {
        _canvas.getContext2d().clearRect(0, 0, 2000, 300);
        _canvas.getContext2d().fillRect(textX, 100, 100, 100);
    }

}
