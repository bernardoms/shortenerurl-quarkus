package com.bernardoms.controller;

import com.bernardoms.exception.AliasNotFoundException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

public class ExceptionGlobalController {

    @Provider
    public static class AliasNotFoundExceptionErrorHandler implements ExceptionMapper<AliasNotFoundException> {

        @Override
        public Response toResponse(AliasNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(mountError(e, "001")).build();
        }
    }

    @Provider
    public static class ConstraintViolationExceptionHandler implements ExceptionMapper<BadRequestException> {

        @Override
        public Response toResponse(BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(mountError(e, "002")).build();
        }
    }

    @Provider
    public static class ExceptionHandler implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mountError(e, "003")).build();
        }
    }

    @Provider
    public static class ThrowableExceptionHandler implements ExceptionMapper<Throwable> {

        @Override
        public Response toResponse(Throwable e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private static HashMap<Object, Object> mountError(Exception e, String errorCode) {
        var error = new HashMap<>();
        error.put("error_code", errorCode);
        error.put("description", e.getMessage());
        return error;
    }
}
