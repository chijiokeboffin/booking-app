package com.element.bookingapplication.core.serviceabstraction;

import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.exception.RecordNotFoundException;
import org.springframework.http.ResponseEntity;

public interface TrailService {

    ResponseEntity<ApiResponse> viewTrails() ;
    ResponseEntity<ApiResponse> viewSelectedTrail(int trailId) ;
}
