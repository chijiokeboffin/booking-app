package com.element.bookingapplication.core.service;


import com.element.bookingapplication.core.daoabstraction.TrailDao;
import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.domain.model.Trail;
import com.element.bookingapplication.core.exception.RecordNotFoundException;
import com.element.bookingapplication.core.serviceabstraction.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TrailServiceImpl implements TrailService {

    private  final TrailDao trailDao;


    public TrailServiceImpl(TrailDao trailDao) {
        this.trailDao = trailDao;
    }

    @Override
    public ResponseEntity<ApiResponse> viewTrails()  {
      var res = trailDao.viewTrails();
      if(Objects.isNull(res)){
          throw  new RecordNotFoundException("No record found");
      }
      ApiResponse response = ApiResponse.builder()
              .data(res)
              .message("success")
              .code(HttpStatus.OK.value())
              .build();
     return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> viewSelectedTrail(int trailId) {
        Optional<Trail> trail = trailDao.viewSelectedTrail(trailId);
        if(trail.isEmpty()){
           throw  new RecordNotFoundException(String.format("Trail with id %s not found", trailId));
        }
        ApiResponse response = ApiResponse.builder()
                .data(trail.get())
                .message("success")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }


}
