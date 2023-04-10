package com.example.test.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnoreProperties({ "hibernateLazyInitializer" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyModel property;

    public boolean isRoomAvailable(Iterable<BookingModel> bookingRecords, LocalDateTime startDate,
            LocalDateTime endDate) {
        // Check if there are any bookings during the given time period
        for (BookingModel booking : bookingRecords) {
            LocalDateTime bookingStartDate = booking.getStartDate();
            LocalDateTime bookingEndDate = booking.getEndDate();
            if ((bookingStartDate.isBefore(endDate) || bookingStartDate.isEqual(endDate)) &&
                    (bookingEndDate.isAfter(startDate) || bookingEndDate.isEqual(startDate))) {
                // Room not available
                return false;
            }
        }
        // Room is available
        return true;
    }

    public List<LocalDateTime[]> getAvailableTimeSlots(BookingModel[] bookings, LocalDateTime startDate,
            LocalDateTime endDate) {
        List<LocalDateTime[]> availableTimeSlots = new ArrayList<>();
        LocalDateTime currentStartDate = startDate;
        for (BookingModel booking : bookings) {
            LocalDateTime bookingStartDate = booking.getStartDate();
            LocalDateTime bookingEndDate = booking.getEndDate();
            if (currentStartDate.isBefore(bookingStartDate)) {
                LocalDateTime[] timeSlot = { currentStartDate, bookingStartDate };
                availableTimeSlots.add(timeSlot);
            }
            currentStartDate = bookingEndDate;
        }
        if (currentStartDate.isBefore(endDate)) {
            LocalDateTime[] timeSlot = { currentStartDate, endDate };
            availableTimeSlots.add(timeSlot);
        }
        return availableTimeSlots;
    }

    public BookingModel() {
    }

    public BookingModel(Long id) {
        this.id = id;
    }

    public BookingModel(LocalDateTime startDate, LocalDateTime endDate, PropertyModel property) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.property = property;
    }

    public BookingModel(PropertyModel property) {
        this.property = property;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public PropertyModel getProperty() {
        return this.property;
    }

    public void setProperty(PropertyModel property) {
        this.property = property;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
