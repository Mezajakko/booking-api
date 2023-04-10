package com.example.test.utils;

import java.time.LocalDateTime;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.test.models.BookingModel;
import com.example.test.utils.constants.DateConstants;
import com.example.test.utils.constants.HttpConstants;
import com.example.test.utils.constants.SystemMessages;

public class ConstraintsValidator {

    public static ResponseBody validateDates(LocalDateTime startDate, LocalDateTime endDate) {

        String message = null;
        int statusCode = 0;
        ResponseBody response = new ResponseBody(statusCode, message);

        if (DateOperators.isBeforeToday(startDate)) {
            response.setMessage(SystemMessages.PAST_DAYS_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return response;
        }

        if (!DateOperators.isEndDateAfterOrEqual(startDate, endDate)) {
            response.setMessage(SystemMessages.END_DATE_LESS_THAN_START_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return response;
        }

        if (!DateOperators.isDifferenceLessOrEqual(LocalDateTime.now(), startDate,
                DateConstants.THIRTY_DAYS_IN_MINUTES.getValue())) {
            response.setMessage(SystemMessages.THIRTY_DAYS_ADVANCED_RESERVATION_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return response;
        }

        if (!DateOperators.isDifferenceLessOrEqual(startDate, endDate,
                DateConstants.THREE_DAYS_IN_MINUTES.getValue())) {
            response.setMessage(SystemMessages.THREE_DAYS_STAY_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return response;
        }

        if (DateOperators.isToday(startDate)) {
            response.setMessage(SystemMessages.RESERVATION_START_NEXT_DAY_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return response;
        }

        return response;

    }

    public static ResponseEntity<ResponseBody> getBookingResponse(BookingModel booking, String operation) {
        ResponseBody response = new ResponseBody();
        Hibernate.initialize(booking); // Force the proxy to load its data
        booking = (BookingModel) Hibernate.unproxy(booking); // Remove the proxy

        if (booking.getId() == null) {
            response.setMessage(SystemMessages.ROOM_NOT_AVAILABLE_ERR_MESSAGE.getValue());
            response.setStatusCode(HttpConstants.BAD_REQUEST.getValue());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setData(booking);
        response.setStatusCode(HttpConstants.OK.getValue());
        if (operation.equals(HttpConstants.PATCH.toString())) {
            response.setMessage(SystemMessages.SUCCESS_MODIFY_RESERVATION_MESSAGE.getValue());
        } else {
            response.setMessage(SystemMessages.SUCCESS_RESERVATION_MESSAGE.getValue());
        }

        return ResponseEntity.ok(response);
    }

}
