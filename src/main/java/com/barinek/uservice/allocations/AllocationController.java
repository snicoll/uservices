package com.barinek.uservice.allocations;

import com.barinek.uservice.BasicHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllocationController extends BasicHandler {
    private final AllocationDAO dao;
    private final ObjectMapper mapper;

    public AllocationController(AllocationDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        post("/allocations", request, httpServletResponse, () -> {
            Allocation allocation = mapper.readValue(request.getReader(), Allocation.class);
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.create(allocation));
        });
        get("/allocations", request, httpServletResponse, () -> {
            String projectId = request.getParameter("projectId");
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.list(Integer.parseInt(projectId)));
        });
    }
}
