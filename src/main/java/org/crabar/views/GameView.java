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

    private void createCanvas() {
        MyCanvas canvas = new MyCanvas();
        addComponent(canvas);
        canvas.setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
