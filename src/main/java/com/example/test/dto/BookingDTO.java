package com.example.test.dto;

import java.time.LocalDateTime;

import com.example.test.utils.DateOperators;

public class BookingDTO {
    private Long roomId;
    private String startDate;
    private String endDate;
    private LocalDateTime parsedStartDate;
    private LocalDateTime parsedEndDate;

    public BookingDTO() {
    }

    public void parseDates() {
        this.parsedStartDate = DateOperators.getDateWithSpecificTime(this.startDate, "00:00:00");
        this.parsedEndDate = DateOperators.getDateWithSpecificTime(this.endDate, "23:59:59");
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getParsedStartDate() {
        return this.parsedStartDate;
    }

    public void setParsedStartDate(LocalDateTime parsedStartDate) {
        this.parsedStartDate = parsedStartDate;
    }

    public LocalDateTime getParsedEndDate() {
        return this.parsedEndDate;
    }

    public void setParsedEndDate(LocalDateTime parsedEndDate) {
        this.parsedEndDate = parsedEndDate;
    }

}
