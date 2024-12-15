package com.maiia.pro.repository;

import com.maiia.pro.entity.Availability;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, String> {
    List<Availability> findByPractitionerId(Integer id);

    @Modifying
    void deleteByPractitionerIdAndStartDateAndEndDate(Integer practitionerId, LocalDateTime startDate, LocalDateTime endDate);

}
