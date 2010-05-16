package org.cloudme.triangle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloudme.triangle.TriangleController;

@SuppressWarnings( "serial" )
public abstract class TriangleServlet extends HttpServlet {
    private TriangleController controller;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getOutputStream().println(controller.getMainEntity().getName());
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init()");
        controller = new TriangleController();
        init(controller);
    }

    protected abstract void init(TriangleController controller);
}
