package com.element.bookingapplication.infrastructure.dao;

import com.element.bookingapplication.core.domain.model.Trail;
import com.element.bookingapplication.infrastructure.rowmapper.TrailRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class TrailDaoImplTest {



    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
    }

    @Test
    void WhenCalledViewTrails_ShouldReturnThreeRecords() {
        TrailDaoImpl trailDao = new TrailDaoImpl(jdbcTemplate);
        assertThat(trailDao.viewTrails().size()).isEqualTo(3);
        assertThat(trailDao.viewTrails().get(0).name()).isEqualTo("Shire");
        assertThat(trailDao.viewTrails().get(1).name()).isEqualTo("Gondor");
        assertThat(trailDao.viewTrails().get(2).name()).isEqualTo("Mordor");
    }

    @Test
    void viewSelectedTrail() {
    }
}