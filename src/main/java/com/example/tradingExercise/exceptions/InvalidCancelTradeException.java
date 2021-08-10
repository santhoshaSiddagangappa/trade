package com.example.tradingExercise.exceptions;

public class InvalidCancelTradeException extends Throwable {
    private String description;

    public InvalidCancelTradeException(String s) {
        this.description = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
