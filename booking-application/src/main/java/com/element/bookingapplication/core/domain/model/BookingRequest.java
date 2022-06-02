package com.element.bookingapplication.core.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public record BookingRequest(
        @NotNull(message = "TrailId is required") int trailId,
        @JsonFormat(pattern="yyyy-MM-dd")  @NotNull(message = "BookDate is required") LocalDate bookDate,
        @NotNull(message = "At least one Hiker is required") Set<Hiker> hikers) {
}
