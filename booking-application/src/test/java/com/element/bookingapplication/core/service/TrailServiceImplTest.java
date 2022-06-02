package com.element.bookingapplication.core.service;

import com.element.bookingapplication.core.daoabstraction.TrailDao;
import com.element.bookingapplication.core.domain.model.ApiResponse;
import com.element.bookingapplication.core.domain.model.Trail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TrailServiceImplTest {

    //Sample unit test
    @Mock
    private  TrailDao trailDao;
    List<Trail> trails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trails = List.of(
                new Trail(1,"Shire", "07:00", "09:00", 29.90),
                new Trail(2,"Gondor", "10:00", "13:00", 59.90),
                new Trail(3,"Mordor", "14:00", "19:00", 99.90));

    }

    @Test
    void WhenCalledViewTrailsReturnThreeRecords() {

        Mockito.when(trailDao.viewTrails()).thenReturn(trails);
        TrailServiceImpl trailService = new TrailServiceImpl(trailDao);
        ResponseEntity<ApiResponse> response = trailService.viewTrails();


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getData()).isEqualTo(trails);
    }

    @Test
    void viewSelectedTrail() {
    }
}