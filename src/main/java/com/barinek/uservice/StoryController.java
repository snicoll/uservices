package com.barinek.uservice;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StoryController extends BasicHandler {
    private final StoryDAO dao;
    private final ObjectMapper mapper;

    public StoryController(StoryDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        post("/stories", request, httpServletResponse, () -> {
            Story story = mapper.readValue(request.getReader(), Story.class);
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.create(story));
        });
        get("/stories", request, httpServletResponse, () -> {
            String accountId = request.getParameter("projectId");
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.list(Integer.parseInt(accountId)));
        });
    }
}
