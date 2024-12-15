package com.maiia.pro.service.impl;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.entity.Availability;
import com.maiia.pro.entity.TimeSlot;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.repository.TimeSlotRepository;
import com.maiia.pro.service.ProAvailabilityService;
import com.maiia.pro.service.utils.Interval;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProAvailabilityServiceImpl implements ProAvailabilityService {

    final private AvailabilityRepository availabilityRepository;
    final private AppointmentRepository appointmentRepository;
    final private TimeSlotRepository timeSlotRepository;

    public List<Availability> findByPractitionerId(Integer practitionerId) {
        return availabilityRepository.findByPractitionerId(practitionerId);
    }

    public List<Availability> generateAvailabilities(Integer practitionerId) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByPractitionerId(practitionerId);
        List<Appointment> appointments = appointmentRepository.findByPractitionerId(practitionerId)
                .stream()
                .sorted(Comparator.comparing(Appointment::getStartDate))
                .collect(Collectors.toList());
        List<Availability> existingAvailabilities = availabilityRepository.findByPractitionerId(practitionerId);

        List<Availability> newAvailabilities = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            generateAvailibiltyForTimeSlot(practitionerId, timeSlot, appointments, existingAvailabilities, newAvailabilities);
        }

        availabilityRepository.saveAll(newAvailabilities);
        return newAvailabilities;
    }

    private void generateAvailibiltyForTimeSlot(Integer practitionerId, TimeSlot timeSlot, List<Appointment> appointments, List<Availability> existingAvailabilities, List<Availability> newAvailabilities) {
        List<Interval> freeIntervals = new ArrayList<>();
        freeIntervals.add(new Interval(timeSlot.getStartDate(), timeSlot.getEndDate()));

        freeIntervals = removeAppointmentsFromInterval(appointments, freeIntervals);

        freeIntervals = removeExistingAvailability(existingAvailabilities, freeIntervals);

        for (int i = 0; i < freeIntervals.size(); i++) {
            Interval free = freeIntervals.get(i);
            boolean isLastInterval = (i == freeIntervals.size() - 1) && free.getEnd().isEqual(timeSlot.getEndDate());
            newAvailabilities.addAll(
                    generateSlotsWithPartialOnlyAtEnd(practitionerId, free.getStart(), free.getEnd(), isLastInterval)
            );
        }
    }

    private List<Interval> removeExistingAvailability(List<Availability> existingAvailabilities, List<Interval> freeIntervals) {
        for (Availability existing : existingAvailabilities) {
            freeIntervals = removeInterval(freeIntervals, existing.getStartDate(), existing.getEndDate());
        }
        return freeIntervals;
    }

    private List<Interval> removeAppointmentsFromInterval(List<Appointment> appointments, List<Interval> freeIntervals) {
        for (Appointment appointment : appointments) {
            freeIntervals = removeInterval(freeIntervals, appointment.getStartDate(), appointment.getEndDate());
        }
        return freeIntervals;
    }

    private List<Interval> removeInterval(List<Interval> freeIntervals, LocalDateTime removeStart, LocalDateTime removeEnd) {
        List<Interval> result = new ArrayList<>();

        for (Interval interval : freeIntervals) {
            if (interval.getStart().isAfter(removeEnd) || removeStart.isAfter(interval.getEnd())) {
                result.add(interval);
            } else {
                // Découpage
                if (interval.getStart().isBefore(removeStart)) {
                    result.add(new Interval(interval.getStart(), removeStart));
                }
                if (interval.getEnd().isAfter(removeEnd)) {
                    result.add(new Interval(removeEnd, interval.getEnd()));
                }
            }
        }

        result.removeIf(i -> !i.getStart().isBefore(i.getEnd()));
        return result;
    }

    private List<Availability> generateSlotsWithPartialOnlyAtEnd(Integer practitionerId,
                                                                 LocalDateTime start,
                                                                 LocalDateTime end,
                                                                 boolean isLastInterval) {
        List<Availability> slots = new ArrayList<>();
        LocalDateTime current = start;

        while (current.plusMinutes(15).isEqual(end) || current.plusMinutes(15).isBefore(end)) {
            LocalDateTime slotEnd = current.plusMinutes(15);
            Availability a = createAvailability(practitionerId, current, slotEnd);
            slots.add(a);
            current = slotEnd;
        }

        // ou cas ou il reste du temps libre sur le dernier interval du timeslot, on peut prendre un rdv qui dépasse la fin du timeslot
        if (current.isBefore(end)) {
            var remainingMinutes = Duration.between(current, end).toMinutes();
            if (isLastInterval && remainingMinutes > 0) {
                Availability partial = createAvailability(practitionerId, current, end);
                slots.add(partial);
            }
        }

        return slots;
    }

    private Availability createAvailability(Integer practitionerId, LocalDateTime startDate, LocalDateTime endDate) {
        return Availability.builder()
                .practitionerId(practitionerId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }


}
