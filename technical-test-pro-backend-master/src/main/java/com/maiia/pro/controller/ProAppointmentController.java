package com.maiia.pro.controller;

import com.maiia.pro.dto.AppointmentDTO;
import com.maiia.pro.mapper.AppointmentMapper;
import com.maiia.pro.service.ProAppointmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProAppointmentController {
    @Autowired
    private ProAppointmentService proAppointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @ApiOperation(value = "Get appointments by practitionerId")
    @GetMapping("/{practitionerId}")
    public List<AppointmentDTO> getAppointmentsByPractitioner(@PathVariable final Integer practitionerId) {
        return appointmentMapper.map(proAppointmentService.findByPractitionerId(practitionerId));
    }

    @ApiOperation(value = "Get all appointments")
    @GetMapping
    public List<AppointmentDTO> getAppointments() {
        return appointmentMapper.map(proAppointmentService.findAll());
    }

    @ApiOperation(value = "Add appointment")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        proAppointmentService.createAppointment(appointmentMapper.map(appointmentDTO));
    }
}
