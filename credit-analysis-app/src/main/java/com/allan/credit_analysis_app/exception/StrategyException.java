package com.allan.credit_analysis_app.exception;

public class StrategyException extends RuntimeException{

    private String observationMessage;

    public StrategyException(String message) {
        super(message);
    }
}
