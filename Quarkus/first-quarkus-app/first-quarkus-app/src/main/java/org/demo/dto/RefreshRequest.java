package org.demo.dto;


import jakarta.enterprise.context.RequestScoped;


public class RefreshRequest {
    public String refreshToken;
    public String userId;
}