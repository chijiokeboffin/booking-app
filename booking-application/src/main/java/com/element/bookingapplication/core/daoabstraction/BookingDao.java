package com.element.bookingapplication.core.daoabstraction;

import com.element.bookingapplication.core.domain.entities.Booking;

import java.time.LocalDate;
import java.util.Optional;


public interface BookingDao {
    Booking bookSelectedTrail(Booking booking);
    Optional<Booking> getBookingByTrailId(int trailId, LocalDate bookDate);
    Booking viewSelectedBooking(Long id);
    int cancelBooking(Long bookingId);
}
