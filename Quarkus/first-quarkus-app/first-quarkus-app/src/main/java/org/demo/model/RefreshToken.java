package org.demo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class RefreshToken extends PanacheEntity {
    public String token;
    public Long userId;
    public Instant expiryDate;
}