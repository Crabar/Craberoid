package org.crabar.components.gwtmycanvas.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by Crabar on 04.11.2014.
 */
public interface CraberoidClientBundle extends ClientBundle {

    public static final CraberoidClientBundle INSTANCE = GWT.create(CraberoidClientBundle.class);

    @Source("images/ball.png")
    public ImageResource ball();
}
