package com.maiia.pro.service.impl;

import com.maiia.pro.entity.Patient;
import com.maiia.pro.repository.PatientRepository;
import com.maiia.pro.service.ProPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProPatientServiceImpl implements ProPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient find(String patientId) {
        return patientRepository.findById(patientId).orElseThrow();
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}