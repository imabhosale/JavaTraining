package org.demo.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class DuplicateUserMapper implements ExceptionMapper<DuplicateUserException> {
    private static final Logger LOG = LoggerFactory.getLogger(DuplicateUserMapper.class);

    @Override
    public Response toResponse(DuplicateUserException exception) {
        LOG.info("Duplicate register: {}", exception.getMessage());
        ErrorResponse error = new ErrorResponse("User already exists ", 409);
        return Response.status(error.status).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}