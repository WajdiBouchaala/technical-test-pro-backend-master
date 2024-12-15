package com.maiia.pro.service;

import com.maiia.pro.entity.Availability;

import java.util.List;

public interface ProAvailabilityService {

    List<Availability> findByPractitionerId(Integer practitionerId);

    List<Availability> generateAvailabilities(Integer practitionerId);
}
