package com.barinek.uservice.projects;

import com.barinek.uservice.BasicHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProjectController extends BasicHandler {
    private final ProjectDAO dao;
    private final ObjectMapper mapper;

    public ProjectController(ProjectDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        post("/projects", request, httpServletResponse, () -> {
            Project project = mapper.readValue(request.getReader(), Project.class);
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.create(project));
        });
        get("/projects", request, httpServletResponse, () -> {
            String accountId = request.getParameter("accountId");
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.list(Integer.parseInt(accountId)));
        });
    }
}