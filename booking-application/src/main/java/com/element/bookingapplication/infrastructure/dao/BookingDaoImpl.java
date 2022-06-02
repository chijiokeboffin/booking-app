package com.element.bookingapplication.infrastructure.dao;

import com.element.bookingapplication.core.daoabstraction.BookingDao;
import com.element.bookingapplication.core.domain.entities.Booking;
import com.element.bookingapplication.core.domain.model.Hiker;
import com.element.bookingapplication.core.exception.DatabaseAccessException;
import com.element.bookingapplication.infrastructure.rowmapper.BookingRowMapper;
import com.element.bookingapplication.infrastructure.rowmapper.HikeRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookingDaoImpl implements BookingDao {

    private final JdbcTemplate jdbcTemplate;

    public BookingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    @Override
    public Booking bookSelectedTrail(Booking booking) {
      try{
          KeyHolder holder = new GeneratedKeyHolder();
          var insertQuery = """
              INSERT INTO bookings (trail_id, amount, book_date,created_on, is_canceled)
              VALUES (?, ?, ?, ?,?);               
              """;

          jdbcTemplate.update(connection -> {
              PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] { "id" });
              ps.setInt(1, booking.getTrailId());
              ps.setDouble(2, booking.getAmount());
              ps.setDate(3, Date.valueOf(booking.getBookDate()));
              ps.setTimestamp(4,Timestamp.valueOf (LocalDateTime.now()));
              ps.setBoolean(5,false);
              return ps;
          }, holder);

          int id = holder.getKey().intValue();
          booking.setId(id);


          if(id > 0){
              List<Hiker> hikers = booking.getHikers().stream().collect(Collectors.toList());
              var insertHikersQuery = """
                      INSERT INTO booking_hikers 
                      (booking_id, first_name, last_name, age)
                       values(?,?,?,?)
                       """;
              this.jdbcTemplate.batchUpdate(insertHikersQuery,
                      new BatchPreparedStatementSetter() {

                          public void setValues(PreparedStatement ps, int i) throws SQLException {
                              ps.setInt(1, id);
                              ps.setString(2, hikers.get(i).firstName());
                              ps.setString(3, hikers.get(i).lastName());
                              ps.setInt(4, hikers.get(i).age());
                          }

                          public int getBatchSize() {
                              return hikers.size();
                          }

                      });
          }
          booking.setId(id);
          return booking;
      }
      catch (Exception ex){
          throw new DatabaseAccessException("Oops something went wrong");
      }
    }

    @Override
    public Optional<Booking> getBookingByTrailId(int trailId, LocalDate bookDate){
      try{
          var selectQuery = """
                    SELECT id, trail_id, amount, book_date, is_canceled
                    FROM bookings
                    WHERE trail_id = ? AND  book_date = ?
                    """;
         var bookingList = jdbcTemplate.query(selectQuery,
                  new BookingRowMapper(), trailId, bookDate);
        return   bookingList.stream().findFirst();

      }catch (Exception ex){
          throw new DatabaseAccessException("Oops something went wrong");
      }
    }

    @Override
    public  Booking viewSelectedBooking(Long  id) {
        Booking booking = null;
        try {
            var selectQuery = """
                    SELECT id, trail_id, amount, book_date, is_canceled
                    FROM bookings
                    WHERE id = ?
                    """;
            booking = jdbcTemplate.queryForObject(selectQuery,
                    new RowMapper<Booking>() {

                        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Booking rcptHdr = new Booking();
                            rcptHdr.setId(rs.getInt("id"));
                            rcptHdr.setTrailId(rs.getInt("trail_id"));
                            rcptHdr.setAmount(rs.getDouble("amount"));
                            rcptHdr.setBookDate(LocalDate.parse(rs.getString("book_date")));
                            rcptHdr.setCanceled(rs.getBoolean("is_canceled"));
                            return rcptHdr;
                        }
                    }, id);

            var hikersSelectQuery = """
                    SELECT id, booking_id, first_name, last_name, age
                    FROM booking_hikers
                    WHERE booking_id = ?
                    """;
            booking.setHikers((jdbcTemplate.query(hikersSelectQuery, new HikeRowMapper() {
                public Hiker mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Hiker hiker = new Hiker(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age")
                    );

                    return hiker;
                }
            }, id)));

            return booking;

        } catch (Exception e) {
            throw new DatabaseAccessException("Oops something went wrong");
        }
    }

    @Override
    public int cancelBooking(Long bookingId){
      try{
          var updateQuery = """
              UPDATE bookings
              SET is_canceled = false
              WHERE id = ?
              """;
          return jdbcTemplate.update(updateQuery, bookingId);
      }catch (Exception ex){
          throw new DatabaseAccessException("Oops something went wrong");
      }
    }

}
