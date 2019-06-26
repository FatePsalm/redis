package com.solace.redisson.redis;

public class UnableToAquireLockException extends RuntimeException {
    public UnableToAquireLockException() {
    }
    public UnableToAquireLockException(String message) {
        super(message);
    }
    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}