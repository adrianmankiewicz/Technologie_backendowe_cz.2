package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient; // Relacja dwustronna

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor; // Relacja dwustronna

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
    private List<MedicalTreatmentEntity> treatments; // Relacja jednostronna od strony rodzica


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

    public DoctorEntity getDoctor() {
		return doctor;
    }

	public Collection<MedicalTreatmentEntity> getTreatments() {
		return treatments;
	}

    public void setPatient(PatientEntity patient2) {
		this.patient = patient2;
    }

    public void setDoctor(DoctorEntity doctor2) {
		this.doctor = doctor2;
    }

    public void setPatientId(Long patientId) {
		this.patient.setId(patientId);
    }

}
