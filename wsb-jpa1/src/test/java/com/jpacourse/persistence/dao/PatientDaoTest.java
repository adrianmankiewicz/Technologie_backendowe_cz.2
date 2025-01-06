package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;

    @Test
    @Transactional
    public void testAddVisit() {
        // given
        Long patientId = 1L;
        Long doctorId = 1L;
        LocalDateTime visitTime = LocalDateTime.of(2023, 12, 1, 10, 0);
        String description = "Routine Checkup";

        // when
        patientDao.addVisit(patientId, doctorId, visitTime, description);

        // then
        PatientEntity patient = patientDao.findOne(patientId);
        assertThat(patient).isNotNull();
        assertThat(patient.getVisits()).hasSize(3);

        VisitEntity visit = patient.getVisits().stream()
                .filter(v -> v.getDescription().equals(description))
                .findFirst()
                .orElse(null);

        assertThat(visit).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDoctor().getId()).isEqualTo(doctorId);
    }
}