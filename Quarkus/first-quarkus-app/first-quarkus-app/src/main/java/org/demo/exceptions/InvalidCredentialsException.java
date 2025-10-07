package org.demo.exceptions;
// New sub-package for cleanliness

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}