package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;

import java.util.List;

public interface ProAppointmentService {

    Appointment find(String appointmentId);

    List<Appointment> findAll();

    List<Appointment> findByPractitionerId(Integer practitionerId);

    void createAppointment(Appointment appointment);
}
