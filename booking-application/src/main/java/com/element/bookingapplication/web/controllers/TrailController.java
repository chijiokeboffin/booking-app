package com.element.bookingapplication.web.controllers;

import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.exception.RecordNotFoundException;
import com.element.bookingapplication.core.serviceabstraction.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/trails")
public class TrailController {


    private final TrailService trailService;


    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> viewTrails()  {
        return trailService.viewTrails();
    }
    @GetMapping(path = "{trailId}")
    public  ResponseEntity<ApiResponse> viewTrailById(@PathVariable("trailId") int trailId) {
        return  trailService.viewSelectedTrail(trailId);
    }

}
