package com.example.tradingExercise.enums;

public enum  EventEnum {

    BUY,
    SELL,
    CANCEL;

    public static EventEnum getEvent(String event)
    {
        switch (event)
        {
            case "BUY" :
                return BUY;
            case "SELL" :
                return SELL;
            case "CANCEL" :
                return CANCEL;
            default:
                return null;
        }
    }
}
