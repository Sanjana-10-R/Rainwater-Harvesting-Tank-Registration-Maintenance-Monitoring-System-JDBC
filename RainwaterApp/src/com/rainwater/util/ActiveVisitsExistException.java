package com.rainwater.util;


public class ActiveVisitsExistException extends Exception {

    public ActiveVisitsExistException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ActiveVisitsExistException: " + getMessage();
    }
}
