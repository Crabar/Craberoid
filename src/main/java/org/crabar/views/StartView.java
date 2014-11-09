package org.crabar.views;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.crabar.ViewController;

/**
 * Created by Crabar on 01.11.2014.
 */
@Theme("mytheme")
public class StartView extends VerticalLayout implements View {

    public StartView() {
        setSizeFull();
        setMargin(true);
        createTitle();
        createMenu();
    }

    private void createMenu() {
        VerticalLayout menu = new VerticalLayout();
        menu.setSpacing(true);
        menu.setWidth(150, Unit.PIXELS);
        menu.setHeightUndefined();
        addComponent(menu);
        setExpandRatio(menu, 1);
        setComponentAlignment(menu, Alignment.MIDDLE_CENTER);
        Button startGameButton = new Button("Start game", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ViewController.getInstance().changeView(ViewController.GAME_VIEW);
            }
        });
        startGameButton.setWidth(100, Unit.PERCENTAGE);
        menu.addComponent(startGameButton);

        Button achievementsButton = new Button("Achievements", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //
            }
        });
        achievementsButton.setEnabled(false);
        achievementsButton.setDescription("The achievements will be soon...");
        achievementsButton.setWidth(100, Unit.PERCENTAGE);
        menu.addComponent(achievementsButton);
    }

    private void createTitle() {
        Label label = new Label("Craberoid 2.0");
        label.setStyleName("title-style");
        addComponent(label);
        label.setWidth(null);
        label.setHeight(null);
        setComponentAlignment(label, Alignment.TOP_CENTER);
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
