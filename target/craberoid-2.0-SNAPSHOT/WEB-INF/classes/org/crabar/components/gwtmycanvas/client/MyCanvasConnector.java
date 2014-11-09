package org.crabar.components.gwtmycanvas.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import org.crabar.components.gwtmycanvas.MyCanvas;

/**
 * Created by Crabar on 08.11.2014.
 */
@Connect(MyCanvas.class)
public class MyCanvasConnector extends AbstractComponentConnector {


    @Override
    public Widget getWidget() {
        return (GWTMyCanvasWidget) super.getWidget();
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(GWTMyCanvasWidget.class);
    }
}
