package org.demo.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional; // ADD THIS IMPORT
import org.demo.model.RefreshToken;
import org.demo.model.User;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {
    private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    public static class Roles {
        public static final String USER = "User";
    }

    // UPDATED: Add @Transactional for DB ops
    @Transactional
    public TokenPair generateUserToken(String email, String username, Long userId) {
        String accessToken = generateToken(email, username, Roles.USER);
        RefreshToken refresh = RefreshToken.createForUser(userId); // persist() happens here
        LOG.info("Tokens generated for user: {}", username);
        return new TokenPair(accessToken, refresh.token);
    }

    // UPDATED: Add @Transactional for DB ops
    @Transactional
    public String refreshAccessToken(String refreshTokenValue, String userId) {
        RefreshToken refreshToken = RefreshToken.findByToken(refreshTokenValue);
        if (refreshToken == null || !refreshToken.isValid() || !refreshToken.userId.toString().equals(userId)) {
            LOG.warn("Invalid refresh token attempt for userId: {}", userId);
            throw new RuntimeException("Invalid or expired refresh token");
        }

//        RefreshToken.deleteByToken(refreshTokenValue); // DB delete
//        RefreshToken newRefresh = RefreshToken.createForUser(Long.parseLong(userId)); // DB persist

//        LOG.info("Refresh attempt for userId: {}", userId);
//        LOG.info("New Refresh token generated : {}", newRefresh.token);
        User user = User.findById(Long.parseLong(userId));


        String newAccessToken = generateToken(user.email, user.username, Roles.USER);

        LOG.info("Access token refreshed for userId: {}", userId);
        return newAccessToken;
    }

    // Existing (unchanged)
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
            jwtClaims.setExpirationTimeMinutesInTheFuture(2);

            String token = TokenUtils.generateTokenString(jwtClaims);
            LOG.info("Access token generated: {}", token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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