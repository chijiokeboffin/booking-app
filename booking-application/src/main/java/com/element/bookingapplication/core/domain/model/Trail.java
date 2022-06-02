package com.element.bookingapplication.core.domain.model;

public record Trail(
        int id,
        String name,
        String startAt,
        String endAt,
        double unitPrice,
        int minAge
        ) {
}

