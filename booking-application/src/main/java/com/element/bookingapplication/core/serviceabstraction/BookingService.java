package com.element.bookingapplication.core.serviceabstraction;

import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.domain.model.BookingRequest;
import org.springframework.http.ResponseEntity;


public interface BookingService {
    ResponseEntity<ApiResponse> bookTrail(BookingRequest booking);
    ResponseEntity<ApiResponse> getSelectedBooking(Long id);
    ResponseEntity<ApiResponse> cancelBooking(Long id);
}
