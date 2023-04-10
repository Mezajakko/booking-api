package com.example.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.BookingDTO;
import com.example.test.models.BookingModel;
import com.example.test.services.BookingService;
import com.example.test.utils.ConstraintsValidator;
import com.example.test.utils.ResponseBody;
import com.example.test.utils.constants.HttpConstants;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/availability")
    public ResponseEntity<ResponseBody> getAvailabilitySlots(@RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDate(startDate);
        bookingDTO.setEndDate(endDate);
        bookingDTO.parseDates();
        return bookingService.getAvailabilitySlots(bookingDTO.getParsedStartDate(), bookingDTO.getParsedEndDate());
    };

    @PostMapping("")
    public ResponseEntity<ResponseBody> createBooking(@RequestBody BookingDTO bookingDTO) {

        bookingDTO.parseDates();
        ResponseBody response = ConstraintsValidator.validateDates(bookingDTO.getParsedStartDate(),
                bookingDTO.getParsedEndDate());

        if (response.getStatusCode() == HttpConstants.BAD_REQUEST.getValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        BookingModel booking = bookingService.createBooking(bookingDTO);

        return ConstraintsValidator.getBookingResponse(booking, HttpConstants.POST.toString());
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<ResponseBody> updateBooking(@PathVariable Long bookingId,
            @RequestBody BookingDTO bookingDTO) {

        bookingDTO.parseDates();
        ResponseBody response = ConstraintsValidator.validateDates(bookingDTO.getParsedStartDate(),
                bookingDTO.getParsedEndDate());

        if (response.getStatusCode() == HttpConstants.BAD_REQUEST.getValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        BookingModel booking = bookingService.updateBooking(bookingId, bookingDTO);

        return ConstraintsValidator.getBookingResponse(booking, HttpConstants.PATCH.toString());

    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ResponseBody> cancelBooking(@PathVariable Long bookingId) {
        return bookingService.cancelBookingById(bookingId);
    }

}
