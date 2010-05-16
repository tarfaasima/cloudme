package org.cloudme.triangle.client;

import org.cloudme.triangle.TriangleController;
import org.cloudme.triangle.servlet.TriangleServlet;

@SuppressWarnings( "serial" )
public class ClientServlet extends TriangleServlet {
    @Override
    protected void init(TriangleController controller) {
        controller.register(Route.class);
    }
}
