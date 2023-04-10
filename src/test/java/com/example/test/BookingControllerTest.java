package com.example.test;

import com.example.test.controllers.BookingController;
import com.example.test.dto.BookingDTO;
import com.example.test.models.BookingModel;
import com.example.test.services.BookingService;
import com.example.test.utils.DateOperators;
import com.example.test.utils.ResponseBody;
import com.example.test.utils.constants.HttpConstants;
import com.example.test.utils.constants.SystemMessages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void testGetAvailabilitySlots() throws Exception {
        String startDate = "2023-04-10";
        String endDate = "2023-04-15";

        LocalDateTime parsedStartDate = DateOperators.getDateWithSpecificTime("2023-04-10", "00:00:00");
        LocalDateTime parsedEndDate = DateOperators.getDateWithSpecificTime("2023-04-15", "23:59:59");

        ResponseBody responseBody = new ResponseBody();
        responseBody.setMessage(SystemMessages.CHECK_ROOM_AVAILABILITY_MESSAGE.getValue());
        responseBody.setStatusCode(HttpConstants.OK.getValue());

        when(bookingService.getAvailabilitySlots(parsedStartDate, parsedEndDate))
                .thenReturn(ResponseEntity.ok(responseBody));

        mockMvc.perform(get("/bookings/availability")
                .param("startDate", startDate)
                .param("endDate", endDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SystemMessages.CHECK_ROOM_AVAILABILITY_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(200)));
    }

    @Test
    public void testCreateBookingSuccesfull() throws Exception {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(bookingModel);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-04-24\",\"endDate\":\"2023-04-25\",\"propertyId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(SystemMessages.SUCCESS_RESERVATION_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(200)));
    }

    @Test
    public void testReservationMoreThanThreeDays() throws Exception {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-04-24\",\"endDate\":\"2023-05-25\",\"propertyId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is(SystemMessages.THREE_DAYS_STAY_ERR_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    // LocalDateTime cannot be mocked because is a Final Class.
    @Test
    public void testReservationMoreThanThirtyDays() throws Exception {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-05-15\",\"endDate\":\"2023-05-16\",\"propertyId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",
                        is(SystemMessages.THIRTY_DAYS_ADVANCED_RESERVATION_ERR_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    @Test
    public void testReservationInThePast() throws Exception {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-04-02\",\"endDate\":\"2023-04-03\",\"propertyId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",
                        is(SystemMessages.PAST_DAYS_ERR_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    @Test
    public void testReservationEndDateLessThanStartDate() throws Exception {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-04-15\",\"endDate\":\"2023-04-13\",\"propertyId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",
                        is(SystemMessages.END_DATE_LESS_THAN_START_ERR_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    // LocalDateTime cannot be mocked because is a Final Class.
    @Test
    public void testReservationTheNextDay() throws Exception {

        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(1L);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2023-04-09\",\"endDate\":\"2023-04-10\",\"propertyId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message",
                        is(SystemMessages.RESERVATION_START_NEXT_DAY_ERR_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }

    @Test
    public void testCancelBooking() throws Exception {
        Long bookingId = 1L;
        ResponseBody responseBody = new ResponseBody();
        responseBody.setMessage(SystemMessages.RESERVATION_CANCELLED_MESSAGE.getValue());
        responseBody.setStatusCode(HttpConstants.OK.getValue());

        when(bookingService.cancelBookingById(bookingId)).thenReturn(ResponseEntity.ok(responseBody));

        mockMvc.perform(delete("/bookings/{bookingId}", bookingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SystemMessages.RESERVATION_CANCELLED_MESSAGE.getValue())))
                .andExpect(jsonPath("$.statusCode", is(HttpConstants.OK.getValue())));
    }

}
