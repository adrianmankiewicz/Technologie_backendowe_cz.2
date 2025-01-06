package com.jpacourse.service;

import java.util.List;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;

public interface PatientService {
    PatientTO findById(Long id);
    void deletePatient(Long id);
    List<VisitTO> findVisitsByPatientId(Long patientId);
}
