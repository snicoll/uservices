package com.barinek.uservice;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class BasicHandler extends AbstractHandler {
    public void post(String uri, Request request, HttpServletResponse httpServletResponse, Func block) throws IOException {
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            if (uri.equals(request.getRequestURI())) {
                httpServletResponse.setContentType("application/json");
                try {
                    httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
                    block.apply();
                } catch (SQLException e) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                request.setHandled(true);
            }
        }
    }

    public void get(String uri, Request request, HttpServletResponse httpServletResponse, Func block) throws IOException {
        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            if (uri.equals(request.getRequestURI())) {
                httpServletResponse.setContentType("application/json");
                try {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    block.apply();
                } catch (SQLException e) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                request.setHandled(true);
            }
        }
    }

    @FunctionalInterface
    public interface Func {
        void apply() throws SQLException, IOException;
    }
}
