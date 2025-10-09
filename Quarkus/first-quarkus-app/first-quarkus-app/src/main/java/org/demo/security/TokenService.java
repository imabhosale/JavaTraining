package org.demo.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.demo.model.RefreshToken;
import org.demo.model.User;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {
    private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    public static class Roles {
        public static final String USER = "User";
    }

    @Transactional
    public TokenPair generateUserToken(String email, String username, Long userId) {
        String accessToken = generateToken(email, username, Roles.USER);
        RefreshToken refresh = createForUser(userId); // Use new method
        LOG.info("Tokens generated for user: {}", username);
        return new TokenPair(accessToken, refresh.token);
    }

    @Transactional
    public String refreshAccessToken(String refreshTokenValue, String userId) {
        RefreshToken refreshToken = findByToken(refreshTokenValue);
        if (refreshToken == null || !isValid(refreshToken) || !refreshToken.userId.toString().equals(userId)) {
            LOG.warn("Invalid refresh token attempt for userId: {}", userId);
            throw new RuntimeException("Invalid or expired refresh token");
        }

//        deleteByToken(refreshTokenValue); // Use new method
//        RefreshToken newRefresh = createForUser(Long.parseLong(userId)); // Use new method

        User user = User.findById(Long.parseLong(userId));
        if (user == null) {
            LOG.warn("User not found for userId: {}", userId);
            throw new RuntimeException("User not found");
        }
        String newAccessToken = generateToken(user.email, user.username, Roles.USER);

        LOG.info("Access token refreshed for userId: {}", userId);
        return newAccessToken;
    }

    private String generateToken(String subject, String name, String... roles) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer("siddhatech");
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(subject);
            jwtClaims.setClaim(Claims.upn.name(), subject);
            jwtClaims.setClaim(Claims.preferred_username.name(), name);
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
            jwtClaims.setAudience("using-jwt");
            jwtClaims.setExpirationTimeMinutesInTheFuture(2); // 2-min expiry

            String token = TokenUtils.generateTokenString(jwtClaims);
            LOG.info("Access token generated: {}", token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Transactional
    public RefreshToken createForUser(Long userId) {
        RefreshToken refresh = new RefreshToken();
        refresh.token = UUID.randomUUID().toString();
        refresh.userId = userId;
        refresh.expiryDate = Instant.now().plusSeconds(604800); // 7 days
        refresh.persist();
        return refresh;
    }


    public boolean isValid(RefreshToken refreshToken) {
        return refreshToken != null && Instant.now().isBefore(refreshToken.expiryDate);
    }


    public RefreshToken findByToken(String token) {
        return RefreshToken.find("token", token).firstResult();
    }


    @Transactional
    public void deleteByToken(String token) {
        RefreshToken.delete("token", token);
    }

    public static class TokenPair {
        public String accessToken;
        public String refreshToken;

        public TokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}