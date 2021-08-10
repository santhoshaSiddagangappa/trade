package com.example.tradingExercise.exceptions;

public class InvalidSellTradeException extends Throwable {
    private String description;

    public InvalidSellTradeException(String s) {
        this.description = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
