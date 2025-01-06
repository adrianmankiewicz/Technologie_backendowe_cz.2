package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(patient.getVisits()).hasSize(7);

        VisitEntity visit = patient.getVisits().stream()
                .filter(v -> v.getDescription().equals(description))
                .findFirst()
                .orElse(null);

        assertThat(visit).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDoctor().getId()).isEqualTo(doctorId);
    }

    @Test
    public void testFindPatientsByLastName() {
        // given
        String lastName = "Doe";

        // when
        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients).allMatch(patient -> patient.getLastName().equals(lastName));
    }

    @Test
    @Transactional
    public void testOptimisticLockingWithConcurrentUpdates() throws InterruptedException {
        // given
        Long patientId = 1L;
        PatientEntity patient1 = patientDao.findOne(patientId);
        PatientEntity patient2 = patientDao.findOne(patientId);
    
        // when
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch saveLatch = new CountDownLatch(1);
    
        Thread thread1 = new Thread(() -> {
            patient1.setFirstName("ConcurrentUpdate1");
            patientDao.save(patient1);
            saveLatch.countDown();
        });
    
        Thread thread2 = new Thread(() -> {
            try {
                latch.await();
                saveLatch.await();
                patient2.setFirstName("ConcurrentUpdate2");
                patientDao.save(patient2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    
        thread1.start();
        latch.countDown();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    @Test
    @Transactional
    public void testOptimisticLockingWithManualVersionIncrement() {
        // given
        Long patientId = 1L;
        PatientEntity patient = patientDao.findOne(patientId);
        Long initialVersion = patient.getVersion();

        // when
        patient.setFirstName("ManualVersionIncrement");
        patient.setVersion(initialVersion + 1);
        patientDao.save(patient);

        // then
        PatientEntity updatedPatient = patientDao.findOne(patientId);
        assertThat(updatedPatient.getVersion()).isEqualTo(initialVersion + 1);
    }
}