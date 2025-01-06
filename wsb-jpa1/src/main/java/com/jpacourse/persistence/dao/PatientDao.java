package com.jpacourse.persistence.dao;

import java.time.LocalDateTime;

import com.jpacourse.persistence.entity.PatientEntity;

public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisit(Long patientId, Long doctorId, LocalDateTime visitTime, String description);
}