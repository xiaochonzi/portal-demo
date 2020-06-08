package com.example.portal.web.controller;

import com.example.portal.model.auth.AuthUser;
import com.example.portal.model.core.Response;
import com.example.portal.security.service.SecurityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private SecurityFactory securityFactory;

    @RequestMapping
    public Response index() {
        AuthUser current = securityFactory.getCurrentUser();
        return Response.ok(current);
    }

    @RequestMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE')")
    public Response create() {
        return Response.ok("create");
    }
}
