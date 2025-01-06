package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientDao patientDao;

    @Mock
    private VisitDao visitDao;

    @InjectMocks
    @Autowired
    private PatientServiceImpl patientService;

    private PatientEntity patientEntity;

    @BeforeEach
    public void setUp() {
        patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("john.doe@example.com");
        patientEntity.setPatientNumber("P123");
        patientEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patientEntity.setAge(43);

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(1L);
        visitEntity.setDescription("Initial Consultation");
        visitEntity.setPatient(patientEntity);
        patientEntity.setVisits(Collections.singletonList(visitEntity));
    }

    @Test
    @Transactional
    public void testDeletePatient() {
        // given
        when(patientDao.findOne(anyLong())).thenReturn(patientEntity);

        // when
        patientService.deletePatient(1L);

        // then
        verify(patientDao, times(1)).delete(any(PatientEntity.class));
        verify(visitDao, times(0)).delete(any(VisitEntity.class));
    }

    @Test
    public void testFindPatientById() {
        // given
        when(patientDao.findOne(anyLong())).thenReturn(patientEntity);

        // when
        PatientTO patientTO = patientService.findById(1L);

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(1L);
        assertThat(patientTO.getFirstName()).isEqualTo("John");
        assertThat(patientTO.getLastName()).isEqualTo("Doe");
        assertThat(patientTO.getTelephoneNumber()).isEqualTo("123456789");
        assertThat(patientTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(patientTO.getPatientNumber()).isEqualTo("P123");
        assertThat(patientTO.getDateOfBirth()).isEqualTo(LocalDate.of(1980, 1, 1));
        assertThat(patientTO.getAge()).isEqualTo(43);
    }
}