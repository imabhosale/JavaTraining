package org.demo.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InvalidCredentialsMapper implements ExceptionMapper<InvalidCredentialsException> {
    private static final Logger LOG = LoggerFactory.getLogger(InvalidCredentialsMapper.class);

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        LOG.warn("Invalid login: {}", exception.getMessage());
        ErrorResponse error = new ErrorResponse("Invalid credentials—check username/password!", 401);
        return Response.status(error.status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}