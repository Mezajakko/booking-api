package com.example.test.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.test.dto.BookingDTO;
import com.example.test.models.BookingModel;
import com.example.test.models.PropertyModel;
import com.example.test.repositories.BookingRepository;
import com.example.test.repositories.PropertyRepository;
import com.example.test.utils.ResponseBody;
import com.example.test.utils.constants.BookingStatuses;
import com.example.test.utils.constants.HttpConstants;
import com.example.test.utils.constants.SystemMessages;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public BookingModel createBooking(BookingDTO bookingDTO) {

        BookingModel booking = new BookingModel();

        PropertyModel property = propertyRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(EntityNotFoundException::new);

        booking.setProperty(property);
        booking.setStartDate(bookingDTO.getParsedStartDate());
        booking.setEndDate(bookingDTO.getParsedEndDate());
        booking.setStatus(BookingStatuses.BOOK_ACTIVE_STATUS.getValue());

        Iterable<BookingModel> bookingRecords = bookingRepository
                .findActiveBookingsFromTodayOnwards(LocalDateTime.now());

        boolean isAvailable = booking.isRoomAvailable(bookingRecords, booking.getStartDate(), booking.getEndDate());

        if (isAvailable) {
            return bookingRepository.save(booking);
        }

        return new BookingModel();

    }

    public ResponseEntity<ResponseBody> getAvailabilitySlots(LocalDateTime startDate, LocalDateTime endDate) {
        List<LocalDateTime[]> availableTimeSlots = new ArrayList<>();
        LocalDateTime currentStartDate = startDate;

        Iterable<BookingModel> bookingRecords = bookingRepository
                .findBookingsFromTodayOnwards(LocalDateTime.now());

        for (BookingModel booking : bookingRecords) {
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

        ResponseBody responseBody = new ResponseBody(200, SystemMessages.CHECK_ROOM_AVAILABILITY_MESSAGE.getValue(),
                availableTimeSlots);

        return ResponseEntity.ok(responseBody);
    }

    public BookingModel updateBooking(Long bookingId, BookingDTO bookingDTO) {
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow();

        Iterable<BookingModel> bookingRecords = bookingRepository
                .findActiveBookingsFromTodayOnwards(LocalDateTime.now());

        boolean isAvailable = booking.isRoomAvailable(bookingRecords, bookingDTO.getParsedStartDate(),
                bookingDTO.getParsedEndDate());

        if (isAvailable) {
            // update booking information
            booking.setStartDate(bookingDTO.getParsedStartDate());
            booking.setEndDate(bookingDTO.getParsedEndDate());
            return bookingRepository.save(booking);
        }

        return new BookingModel();

    }

    public ResponseEntity<ResponseBody> cancelBookingById(Long id) {
        Optional<BookingModel> optionalBooking = bookingRepository.findByIdAndActive(id);

        ResponseBody response = new ResponseBody();

        if (optionalBooking.isPresent()) {
            BookingModel booking = optionalBooking.get();
            booking.setStatus(BookingStatuses.BOOK_CANCELLED_STATUS.getValue());
            bookingRepository.save(booking);
            response.setMessage(SystemMessages.RESERVATION_CANCELLED_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.OK.getValue());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
