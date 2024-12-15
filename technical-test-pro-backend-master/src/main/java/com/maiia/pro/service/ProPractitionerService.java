package com.maiia.pro.service;

import com.maiia.pro.entity.Practitioner;

import java.util.List;

public interface ProPractitionerService {

    Practitioner find(String practitionerId);

    List<Practitioner> findAll();
}
