package com.maiia.pro.service.impl;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.service.ProAppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ProAppointmentServiceImpl implements ProAppointmentService {

    final private AppointmentRepository appointmentRepository;
    final private AvailabilityRepository availabilityRepository;

    public Appointment find(String appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByPractitionerId(Integer practitionerId) {
        return appointmentRepository.findByPractitionerId(practitionerId);
    }

    @Transactional
    public void createAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
        availabilityRepository.deleteByPractitionerIdAndStartDateAndEndDate(appointment.getPractitionerId(), appointment.getStartDate(), appointment.getEndDate());

    }

}
