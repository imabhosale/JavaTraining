package org.demo.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.dto.LoginRequest;
import org.demo.dto.LoginResponse;
import org.demo.dto.RefreshRequest;
import org.demo.model.RefreshToken;
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
        if (User.find("username", user.username).firstResult() != null) {
            throw new DuplicateUserException("User with username '" + user.username + "' already exists!");
        }
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
            throw new InvalidCredentialsException("No user found or password is incorrect");
        }
        TokenService.TokenPair tokens = service.generateUserToken(existingUser.email, existingUser.username, existingUser.id);
        LOG.info("Login success: {}", existingUser.username);
        return Response.ok(new LoginResponse(tokens.accessToken, tokens.refreshToken)).build();
    }

    // UPDATED: Accept JSON body
    @POST
    @Path("/refresh")
    @Transactional // Ensure DB ops are safe
    public Response refresh(RefreshRequest request) {
        LOG.info("Refresh attempt for userId: {}", request.userId);
        String newAccessToken = service.refreshAccessToken(request.refreshToken, request.userId);
        return Response.ok(new LoginResponse(newAccessToken, null)).build();
    }

    @POST
    @Path("/logout")
    @Transactional
    public Response logout(@FormParam("refreshToken") String refreshToken) {
        LOG.info("Logout attempt with refreshToken: {}", refreshToken);
        RefreshToken.deleteByToken(refreshToken);
        return Response.ok("Logged out successfully").build();
    }

 }