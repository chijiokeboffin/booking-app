package com.element.bookingapplication.core.domain.entities;

import com.element.bookingapplication.core.domain.model.Hiker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private int id;
    private int trailId;
    private double amount;
    private LocalDate bookDate;
    private boolean isCanceled;
    private List<Hiker> hikers;
}
