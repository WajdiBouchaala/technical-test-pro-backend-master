package com.maiia.pro.service.impl;

import com.maiia.pro.entity.TimeSlot;
import com.maiia.pro.repository.TimeSlotRepository;
import com.maiia.pro.service.ProTimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProTimeSlotServiceImpl implements ProTimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public List<TimeSlot> findByPractitionerId(Integer practitionerId) {
        return timeSlotRepository.findByPractitionerId(practitionerId);
    }
}

