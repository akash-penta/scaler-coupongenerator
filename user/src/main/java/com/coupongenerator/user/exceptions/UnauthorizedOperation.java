package com.coupongenerator.user.exceptions;

public class UnauthorizedOperation extends Exception {
    public UnauthorizedOperation(String message) {
        super(message);
    }
}
