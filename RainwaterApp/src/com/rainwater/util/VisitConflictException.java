package com.rainwater.util;




public class VisitConflictException extends Exception {

    public VisitConflictException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "VisitConflictException: " + getMessage();
    }
}
