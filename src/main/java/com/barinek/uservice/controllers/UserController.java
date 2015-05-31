package com.barinek.uservice.controllers;

import com.barinek.uservice.dal.UserDAO;
import com.barinek.uservice.utils.BasicHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends BasicHandler {
    private final UserDAO dao;
    private final ObjectMapper mapper;

    public UserController(UserDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        get("/users", request, httpServletResponse, () -> {
            String userId = request.getParameter("userId");
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.show(Integer.parseInt(userId)));
        });
    }
}
