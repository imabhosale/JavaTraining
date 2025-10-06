package org.demo.de;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Utility to generate the JWT token string.
 */
public class JWTTokenGeneration {
    /**
     * JWT token Generation
     */
    public static void main(String[] args) {
        String token = Jwt.issuer("https://example.com/issuer")
                .upn("ishaq@quarkus.io")
                .groups(new HashSet<>(Arrays.asList("User","")))
                .claim(Claims.birthdate.name(), "2002-12-24")
                .sign();
        System.out.println(token);

    }
}