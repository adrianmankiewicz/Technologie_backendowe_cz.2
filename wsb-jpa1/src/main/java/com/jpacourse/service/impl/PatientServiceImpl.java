package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Override
    public void deletePatient(Long id) {
        PatientEntity patient = patientDao.findOne(id);
        if (patient != null) {
            patientDao.delete(patient);
        }
    }

    @Override
    public PatientTO findById(Long id) {
        PatientEntity patient = patientDao.findOne(id);
        if (patient != null) {
            return mapToTO(patient);
        }
        return null;
    }

    private PatientTO mapToTO(PatientEntity patient) {
        PatientTO patientTO = new PatientTO();
        patientTO.setId(patient.getId());
        patientTO.setFirstName(patient.getFirstName());
        patientTO.setLastName(patient.getLastName());
        patientTO.setTelephoneNumber(patient.getTelephoneNumber());
        patientTO.setEmail(patient.getEmail());
        patientTO.setPatientNumber(patient.getPatientNumber());
        patientTO.setDateOfBirth(patient.getDateOfBirth());
        patientTO.setAge(patient.getAge());
        return patientTO;
    }

    @Override
    public List<VisitTO> findVisitsByPatientId(Long patientId) {
        PatientEntity patient = patientDao.findOne(patientId);
        if (patient != null) {
            return patient.getVisits().stream()
                .map(PatientMapper::mapVisitToTO)
                .collect(Collectors.toList());
        }
        return null;
    }
}