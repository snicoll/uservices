package com.barinek.uservices.accounts;

import com.barinek.uservices.restsupport.BasicHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountController extends BasicHandler {
    private final AccountDAO dao;
    private final ObjectMapper mapper;

    public AccountController(AccountDAO dao) {
        this.dao = dao;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        get("/accounts", request, httpServletResponse, () -> {
            String ownerId = request.getParameter("ownerId");
            mapper.writeValue(httpServletResponse.getOutputStream(), dao.findFor(Integer.parseInt(ownerId)));
        });
    }
}
