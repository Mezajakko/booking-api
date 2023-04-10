package com.example.test.utils.constants;

public enum SystemMessages {
    PAST_DAYS_ERR_MESSAGE("It is not possible to make reservations for past days."),
    END_DATE_LESS_THAN_START_ERR_MESSAGE("End date cannot be less than the Start date."),
    THIRTY_DAYS_ADVANCED_RESERVATION_ERR_MESSAGE("The room cannot be reserved more than 30 days in advance."),
    THREE_DAYS_STAY_ERR_MESSAGE("You can only stay in the room for 3 days or less."),
    RESERVATION_START_NEXT_DAY_ERR_MESSAGE("All Reservations must start at least the next day of booking."),
    ROOM_NOT_AVAILABLE_ERR_MESSAGE("The room is not available for the selected dates."),
    SUCCESS_RESERVATION_MESSAGE("Reservation placed successfully."),
    SUCCESS_MODIFY_RESERVATION_MESSAGE("Reservation updated successfully."),
    RESERVATION_CANCELLED_MESSAGE("Reservation successfully cancelled."),
    CHECK_ROOM_AVAILABILITY_MESSAGE("The list of the time slots available for the room.");

    private final String value;

    SystemMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
