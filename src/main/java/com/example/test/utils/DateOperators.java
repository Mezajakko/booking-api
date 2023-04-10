package com.example.test.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateOperators {

    public static boolean isEndDateAfterOrEqual(LocalDateTime startDate, LocalDateTime endDate) {
        return endDate.isEqual(startDate) || endDate.isAfter(startDate);
    }

    public static boolean isDifferenceLessOrEqual(LocalDateTime startDate, LocalDateTime endDate,
            int minutesComparison) {
        Duration duration = Duration.between(startDate, endDate);
        long diff = Math.abs(duration.toMinutes());
        return diff <= minutesComparison;
    }

    public static LocalDateTime getDateWithSpecificTime(String dateString, String timeString) {

        LocalDate date = LocalDate.parse(dateString);
        LocalTime time = LocalTime.parse(timeString);

        // Combine the date and time into a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        return dateTime;
    }

    public static boolean isToday(LocalDateTime date) {
        LocalDate today = LocalDate.now();
        LocalDate dateToCheck = date.toLocalDate();
        return today.equals(dateToCheck);
    }

    public static boolean isBeforeToday(LocalDateTime date) {
        LocalDateTime today = LocalDateTime.now();
        return date.isBefore(today.with(LocalTime.MIN));
    }

}
