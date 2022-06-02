package com.element.bookingapplication.infrastructure.dao;

import com.element.bookingapplication.core.domain.entities.Booking;
import com.element.bookingapplication.core.domain.model.Hiker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class BookingDaoImplTest {

    //Sample integration test

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void WhenCalledBookSelectedTrail_ShouldPass() {
        //Arrange
        BookingDaoImpl bookingDao = new BookingDaoImpl(jdbcTemplate);
        List<Hiker> hikers = List.of(
                new Hiker("Nicolas", "Dean", 30),
                new Hiker("John", "Doe", 25),
                new Hiker("Mary", "Doe", 45)
                );
        Booking booking = new Booking(0,1, 300, LocalDate.now(), false, hikers);

        //Act
        var actual = bookingDao.bookSelectedTrail(booking);

        //Assert
        assertThat(actual.getId()).isGreaterThan(0);
    }

    @Test
    void getBookingByTrailId() {
    }

    @Test
    void viewSelectedBooking() {
    }

    @Test
    void cancelBooking() {
    }
}