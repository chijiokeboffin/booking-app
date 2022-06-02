package com.element.bookingapplication.infrastructure.dao;

import com.element.bookingapplication.core.daoabstraction.TrailDao;
import com.element.bookingapplication.core.domain.model.Trail;
import com.element.bookingapplication.infrastructure.rowmapper.TrailRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrailDaoImpl implements TrailDao {

    private  final JdbcTemplate jdbcTemplate;


    public TrailDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Trail> viewTrails() {
        var selectQuery = """
                 SELECT * FROM trails
                """;
       return jdbcTemplate.query(selectQuery, new TrailRowMapper());
    }

    @Override
    public Optional<Trail>  viewSelectedTrail(int trailId) {
        var selectQuery = """
                 SELECT * FROM trails Where id = ?
                """;
       return jdbcTemplate.query(selectQuery, new TrailRowMapper(), trailId).stream().findFirst();
    }
}
