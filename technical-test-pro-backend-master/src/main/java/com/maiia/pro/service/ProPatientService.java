package com.maiia.pro.service;

import com.maiia.pro.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProPatientService {

    Patient find(String patientId);

    List<Patient> findAll();
}
