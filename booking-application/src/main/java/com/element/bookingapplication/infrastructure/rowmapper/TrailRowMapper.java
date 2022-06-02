package com.element.bookingapplication.infrastructure.rowmapper;

import com.element.bookingapplication.core.domain.model.Trail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class TrailRowMapper implements RowMapper<Trail> {
    @Override
    public Trail mapRow(ResultSet rs, int rowNum) throws SQLException {
        DecimalFormat decim = new DecimalFormat("0.00");
        return new Trail(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("start_at"),
                rs.getString("end_at"),
                Double.parseDouble(decim.format( rs.getDouble("unit_price"))),
                rs.getInt("min_age")
        );
    }
}
