package com.element.bookingapplication.web.controllers;

import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.domain.model.BookingRequest;
import com.element.bookingapplication.core.serviceabstraction.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/booking")
public class BookingController {


    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/book-trail")
    public ResponseEntity<ApiResponse> bookSelectTrail(@Valid @RequestBody BookingRequest request){
        return this .bookingService.bookTrail(request);
    }

    @GetMapping(path = "/view-booking/{bookingId}")
    public  ResponseEntity<ApiResponse> viewBooking(@PathVariable("bookingId") Long bookingId){
      return  this.bookingService.getSelectedBooking(bookingId);
    }

    @GetMapping(path = "/cancel-booking/{bookingId}")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable("bookingId") Long bookingId){
          return this.bookingService.cancelBooking(bookingId);
    }

}
