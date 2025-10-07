package org.demo.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.model.User;  // Your User entity
import org.demo.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.demo.exceptions.InvalidCredentialsException;  // <-- ADD IMPORTS
import org.demo.exceptions.DuplicateUserException;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    TokenService service;

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        LOG.info("Register attempt: {}", user.login);

        // Check duplicate
        if (User.find("login", user.login).firstResult() != null) {
            throw new DuplicateUserException("User with login '" + user.login + "' already exists!");  // <-- NOW RESOLVES
        }

        // Hash password
        user.password = BcryptUtil.bcryptHash(user.password);
        user.persist();

        LOG.info("Registered: {}", user.login);
        return Response.ok(user).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        LOG.info("Login attempt: {}", request.login);

        User existingUser = User.find("login", request.login).firstResult();
        if (existingUser == null || !BcryptUtil.matches(request.password, existingUser.password)) {
            throw new InvalidCredentialsException("No user found or password is incorrect");  // <-- NOW RESOLVES
        }

        String token = service.generateUserToken(existingUser.email, existingUser.login);
        LOG.info("Login success: {}", existingUser.login);

        return Response.ok(new LoginResponse(token)).build();
    }

    // Helpers (same as before)
    public static class LoginRequest {
        public String login;
        public String password;
    }

    public static class LoginResponse {
        public String token;
        public LoginResponse(String token) { this.token = token; }
    }
}