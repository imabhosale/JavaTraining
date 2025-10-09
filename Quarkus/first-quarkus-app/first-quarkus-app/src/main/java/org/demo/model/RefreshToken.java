package org.demo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class RefreshToken extends PanacheEntity {
    public String token;  // Opaque UUID string
    public Long userId;  // Links to User.id
    public Instant expiryDate;  // When it expires (e.g., 7 days)

    // Factory to create for a user
    public static RefreshToken createForUser(Long userId) {
        RefreshToken refresh = new RefreshToken();
        refresh.token = UUID.randomUUID().toString();  // Random, secure string
        refresh.userId = userId;
        refresh.expiryDate = Instant.now().plusSeconds(604800);  // 7 days (adjust as needed)
        refresh.persist();
        return refresh;
    }

    // Check if valid
    public boolean isValid() {
        return Instant.now().isBefore(expiryDate);
    }

    // Find by token
    public static RefreshToken findByToken(String token) {
        return find("token", token).firstResult();
    }

    // Delete by token for logout/rotation
    public static void deleteByToken(String token) {
        delete("token", token);
    }
}