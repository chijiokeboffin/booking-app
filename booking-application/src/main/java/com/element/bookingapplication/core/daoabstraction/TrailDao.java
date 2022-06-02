package com.element.bookingapplication.core.daoabstraction;

import com.element.bookingapplication.core.domain.model.Trail;

import java.util.List;
import java.util.Optional;

public interface TrailDao {
    List<Trail> viewTrails();
    Optional<Trail> viewSelectedTrail(int trailId);
}
