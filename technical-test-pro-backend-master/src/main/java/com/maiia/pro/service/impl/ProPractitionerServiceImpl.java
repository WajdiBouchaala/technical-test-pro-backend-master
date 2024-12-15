package com.maiia.pro.service.impl;

import com.maiia.pro.entity.Practitioner;
import com.maiia.pro.repository.PractitionerRepository;
import com.maiia.pro.service.ProPractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProPractitionerServiceImpl implements ProPractitionerService {

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Override
    public Practitioner find(String practitionerId) {
        return practitionerRepository.findById(practitionerId).orElseThrow();
    }

    @Override
    public List<Practitioner> findAll() {
        return practitionerRepository.findAll();
    }
}
