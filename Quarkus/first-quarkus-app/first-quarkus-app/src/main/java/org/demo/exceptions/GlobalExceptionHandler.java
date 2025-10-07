package org.demo.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        LOG.error("App crash: {}", exception.getMessage(), exception);
        ErrorResponse error = new ErrorResponse("Something went wrong—try again!", 500);
        return Response.status(error.status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}