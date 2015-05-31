package com.barinek.uservices.accounts;

import com.barinek.uservices.restsupport.BasicHandler;
import com.barinek.uservices.users.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationController extends BasicHandler {
    private final RegistrationService service;
    private final ObjectMapper mapper;

    public RegistrationController(RegistrationService service) {
        this.service = service;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        post("/registration", request, httpServletResponse, () -> {
            User user = mapper.readValue(request.getReader(), User.class);
            mapper.writeValue(httpServletResponse.getOutputStream(), service.createUserWithAccount(user));
        });
    }
}
