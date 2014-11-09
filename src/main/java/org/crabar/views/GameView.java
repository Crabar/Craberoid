package org.crabar.views;

import com.google.gwt.user.client.Timer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import com.vaadin.ui.VerticalLayout;
import org.crabar.components.gwtmycanvas.MyCanvas;
import org.vaadin.hezamu.canvas.Canvas;


/**
 * Created by Crabar on 01.11.2014.
 */
public class GameView extends VerticalLayout implements View {
    private static final int FRAMERATE = 60;

    public GameView() {
        setSizeFull();
        setStyleName("game-view");
        createCanvas();

    }

    private void initGameTimer() {
//        _timer = new Timer() {
//            @Override
//            public void run() {
//                textX += 1;
//                drawCanvas();
//            }
//        };
//
//        _timer.scheduleRepeating(1000);
    }

    private void createCanvas() {
//        _canvas = new Canvas();
//        addComponent(_canvas);
        //
        MyCanvas test = new MyCanvas();
        addComponent(test);
    }

    private int textX;

    private void drawCanvas() {
        _canvas.saveContext();
        _canvas.clear();
        _canvas.fillRect(textX, 40, 100, 100);
        _canvas.restoreContext();
    }

    private Canvas _canvas;
//    private Timer _timer;

    private void createCrab() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        _canvas.setWidth(getWidth(), Unit.PERCENTAGE);
//        _canvas.setHeight(getHeight(), Unit.PERCENTAGE);
//        initGameTimer();
    }
}
