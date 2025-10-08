package org.demo.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.dto.LoginRequest;
import org.demo.dto.LoginResponse;
import org.demo.model.User;  // Your User entity
import org.demo.repository.UserRepository;
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

    @Inject
    UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        LOG.info("Register attempt: {}", user.username);

        // Check duplicate
        if (User.find("username", user.username).firstResult() != null) {
            throw new DuplicateUserException("User with login '" + user.username + "' already exists!");  // <-- NOW RESOLVES
        }

        // Hash password
        user.password = BcryptUtil.bcryptHash(user.password);
        user.persist();

        LOG.info("Registered: {}", user.username);
        return Response.ok(user).build();
    }

    @POST
    @Path("/test")
    public Response testMapping(LoginRequest req) {
        LOG.info("Mapped request: username={}, password={}", req.username, req.password);
        return Response.ok(req).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {

        LOG.info("Login payload: username={}, password={}", request.username, request.password);
        LOG.info("Login attempt: {}", request.username);

        User existingUser = User.find("username", request.username).firstResult();
        if (existingUser == null || !BcryptUtil.matches(request.password, existingUser.password)) {
            throw new InvalidCredentialsException("No user found or password is incorrect");          }

        String token = service.generateUserToken(existingUser.email, existingUser.username);
        LOG.info("Login success: {}", existingUser.username);

        return Response.ok(new LoginResponse(token)).build();
    }


 }