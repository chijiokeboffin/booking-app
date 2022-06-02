package com.element.bookingapplication.infrastructure.rowmapper;

import com.element.bookingapplication.core.domain.model.Hiker;
import com.element.bookingapplication.core.domain.model.Trail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HikeRowMapper implements RowMapper<Hiker> {

    @Override
    public Hiker mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Hiker(
                rs.getString("start_at"),
                rs.getString("end_at"),
                rs.getInt("unit_price")
        );
    }
}
