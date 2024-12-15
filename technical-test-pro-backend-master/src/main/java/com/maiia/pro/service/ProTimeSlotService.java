package com.maiia.pro.service;

import com.maiia.pro.entity.TimeSlot;

import java.util.List;

public interface ProTimeSlotService {

    List<TimeSlot> findByPractitionerId(Integer practitionerId);
}
