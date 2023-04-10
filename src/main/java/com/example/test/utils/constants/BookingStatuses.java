package com.example.test.utils.constants;

public enum BookingStatuses {
    BOOK_ACTIVE_STATUS("Active"),
    BOOK_CANCELLED_STATUS("Cancelled");

    private final String value;

    BookingStatuses(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
