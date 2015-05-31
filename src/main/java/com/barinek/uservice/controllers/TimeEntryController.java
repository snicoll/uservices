package com.barinek.uservice.controllers;

import com.barinek.uservice.dal.TimeEntryDAO;
import com.barinek.uservice.models.TimeEntry;
import com.barinek.uservice.utils.BasicHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TimeEntryController extends BasicHandler {
    private final TimeEntryDAO dao;
    private final ObjectMapper mapper;

    public TimeEntryController(TimeEntryDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        post("/time-entries", request, httpServletResponse, () -> {
            TimeEntry entry = mapper.readValue(request.getReader(), TimeEntry.class);
            TimeEntry value = dao.create(entry);
            mapper.writeValue(httpServletResponse.getOutputStream(), value);
        });
        get("/time-entries", request, httpServletResponse, () -> {
            String userId = request.getParameter("userId");
            List<TimeEntry> value = dao.list(Integer.parseInt(userId));
            mapper.writeValue(httpServletResponse.getOutputStream(), value);
        });
    }
}
