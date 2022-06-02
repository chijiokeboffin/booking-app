package com.element.bookingapplication.core.service;


import com.element.bookingapplication.core.daoabstraction.BookingDao;
import com.element.bookingapplication.core.daoabstraction.TrailDao;
import com.element.bookingapplication.core.domain.entities.Booking;
import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.domain.model.BookingRequest;
import com.element.bookingapplication.core.domain.model.Hiker;
import com.element.bookingapplication.core.domain.model.Trail;
import com.element.bookingapplication.core.exception.InvalidHikerAgeException;
import com.element.bookingapplication.core.exception.RecordNotFoundException;
import com.element.bookingapplication.core.serviceabstraction.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final TrailDao trailDao;



    public BookingServiceImpl(BookingDao bookingDao, TrailDao trailDao) {
        this.bookingDao = bookingDao;
        this.trailDao = trailDao;
    }


    @Override
    public ResponseEntity<ApiResponse> bookTrail(BookingRequest booking){
        Optional<Trail> trail = trailDao.viewSelectedTrail(booking.trailId());
        if(trail.isEmpty()){
            throw new IllegalArgumentException("Select Trail is invalid");
        }
        validateHikerAge(trail.get().minAge(), booking.hikers());

        if(booking.bookDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("You can only book future date");
        }

        //Check if the there is existing trail booking
      Optional<Booking> activeBooking = this.bookingDao.getBookingByTrailId(booking.trailId(), booking.bookDate());
        if(activeBooking.isPresent()){
            throw new IllegalArgumentException(String.format("Selected Trail already booked for the date %s", booking.bookDate()));
        }

        double amount = trail.get().unitPrice()  * booking.hikers().size();
        var hikers = booking.hikers().stream().collect(Collectors.toList());
        Booking newBooking = new Booking(
                0,
                booking.trailId(),
                amount,
                booking.bookDate(),
                false,
                hikers
        );

      var retVal =  this.bookingDao.bookSelectedTrail(newBooking);

          ApiResponse response = ApiResponse
                  .builder()
                  .data(retVal)
                  .code(HttpStatus.CREATED.value())
                  .message("Booked successfully")
                  .build();
          return  ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Override
    public ResponseEntity<ApiResponse> getSelectedBooking(Long id){
        //String nam = BookingServiceImpl.class.getName();
        var booking = bookingDao.viewSelectedBooking(id);
        if(Objects.isNull(booking)){
            throw new RecordNotFoundException("No booking found with selected id $s".formatted(id));
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(booking)
                .code(HttpStatus.OK.value())
                .message("success")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> cancelBooking(Long id){

        var booking = bookingDao.viewSelectedBooking(id);
        if(Objects.isNull(booking)){
            throw new RecordNotFoundException("No booking record found with id %s".formatted(id));
        }
        if(booking.isCanceled()){
            throw new IllegalStateException("Selected booking has been canceled");
        }

        int retVal = bookingDao.cancelBooking(id);
        if(retVal == 1){
            ApiResponse apiResponse = ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Canceled successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        else
        {
            HttpStatus expectationFailed = HttpStatus.EXPECTATION_FAILED;
            ApiResponse apiResponse = ApiResponse.builder()
                    .code(expectationFailed.value())
                    .message("Operation failed")
                    .build();
            return ResponseEntity.status(expectationFailed).body(apiResponse);
        }
    }
    private static void validateHikerAge(int trailMinAge, Set<Hiker> hikers){

        var res = hikers.parallelStream().filter((Hiker h )-> h.age() > 0).collect(Collectors.toList());
        boolean retVal = hikers.stream().anyMatch(h -> h.age() < trailMinAge);
        if(retVal){
            throw new InvalidHikerAgeException("One or more Hiker(s) age is invalid");
        }


    }
}
