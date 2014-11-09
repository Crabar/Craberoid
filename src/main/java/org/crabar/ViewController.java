package org.crabar;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;
import org.crabar.views.GameView;
import org.crabar.views.StartView;

/**
 * Created by Crabar on 01.11.2014.
 */
public class ViewController {
    private static ViewController ourInstance = new ViewController();

    public static ViewController getInstance() {
        return ourInstance;
    }

    public static final String START_VIEW = "startView";
    public static final String GAME_VIEW = "gameView";

    private ViewController() {
    }

    public void init(UI ui) {
        navigator = new Navigator(ui, ui);
        navigator.addView(START_VIEW, new StartView());
        navigator.addView(GAME_VIEW, new GameView());
    }

    Navigator navigator;

    public void changeView(String viewName) {
        navigator.navigateTo(viewName);
    }
}
