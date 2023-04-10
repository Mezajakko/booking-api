package com.example.test.utils.constants;

public enum DateConstants {
    THREE_DAYS_IN_MINUTES(4319),
    THIRTY_DAYS_IN_MINUTES(43199);

    private final int value;

    DateConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
