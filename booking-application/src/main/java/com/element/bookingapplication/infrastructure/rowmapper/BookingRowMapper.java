package com.element.bookingapplication.infrastructure.rowmapper;

import com.element.bookingapplication.core.domain.entities.Booking;
import com.element.bookingapplication.core.domain.model.Hiker;
import com.element.bookingapplication.core.domain.model.Trail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingRowMapper implements RowMapper<Booking> {
    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("trail_id"),
                rs.getDouble("amount"),
                LocalDate.parse(rs.getString("book_date")),
                rs.getBoolean("is_canceled"),
                List.of()
        );
    }
}
