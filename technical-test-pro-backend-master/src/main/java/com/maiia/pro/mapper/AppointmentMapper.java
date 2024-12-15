package com.maiia.pro.mapper;

import com.maiia.pro.dto.AppointmentDTO;
import com.maiia.pro.entity.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment map(AppointmentDTO appointmentDTO);

    AppointmentDTO map(Appointment appointment);

    List<AppointmentDTO> map(List<Appointment> all);
}