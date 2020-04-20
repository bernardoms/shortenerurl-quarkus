package com.bernardoms.controller;

import com.bernardoms.dto.URLShortenerDTO;
import com.bernardoms.service.ShortenerService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/shorteners")

public class ShortenerController {
    
    @Inject
    private ShortenerService shortenerService;

    @GET
    @Path("/{alias}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirect(@PathParam("alias") String alias) throws Exception {
        var url = shortenerService.redirect(alias);
        return Response.status(Response.Status.FOUND)
                .header("Location", url)
                .entity(url)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllShortenedUrl() {
        return Response.status(Response.Status.OK)
                .entity(shortenerService.findAll())
                .build();
    }

    @POST
    public Response create(@RequestBody (required = true) @Valid URLShortenerDTO urlShortenerDTO) {
        String shortenerURL = shortenerService.createShortenerURL(urlShortenerDTO);
        return Response.status(Response.Status.CREATED)
                .header("Location", "/shoreteners/" + shortenerURL)
                .build();
    }
}