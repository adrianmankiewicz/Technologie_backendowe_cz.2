-- Przykładowe dane dla tabeli address
insert into address (id, address_line1, address_line2, city, postal_code)
values (1, 'Main St', 'Apt 101', 'Springfield', '12345'),
       (2, 'Second St', 'Suite 202', 'Shelbyville', '67890');

-- Przykładowe dane dla tabeli patient
insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, age)
values (1, 'John', 'Doe', '123456789', 'john.doe@example.com', 'P123', '1980-01-01', 43),
       (2, 'Jane', 'Smith', '987654321', 'jane.smith@example.com', 'P124', '1990-02-02', 33),
       (3, 'Alice', 'Johnson', '555123456', 'alice.johnson@example.com', 'P125', '1985-03-03', 38),
       (4, 'Bob', 'Brown', '555654321', 'bob.brown@example.com', 'P126', '1975-04-04', 48),
       (5, 'Charlie', 'Doe', '555987654', 'charlie.doe@example.com', 'P127', '2000-05-05', 23);

-- Przykładowe dane dla tabeli doctor
insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization)
values (1, 'Alice', 'Johnson', '555123456', 'alice.johnson@example.com', 'D001', 'OCULIST'),
       (2, 'Bob', 'Brown', '555654321', 'bob.brown@example.com', 'D002', 'DERMATOLOGIST');

-- Przykładowe dane dla tabeli visit
insert into visit (id, description, time, patient_id, doctor_id)
values (1, 'Initial Consultation', '2023-10-01T10:00:00', 1, 1),
       (2, 'Follow-up Visit', '2023-10-15T11:00:00', 1, 2),
       (3, 'Routine Checkup', '2023-11-01T09:00:00', 2, 1),
       (4, 'Specialist Consultation', '2023-11-20T14:00:00', 3, 2),
       (5, 'Dental Cleaning', '2023-12-01T10:00:00', 1, 1),
       (6, 'Eye Exam', '2023-12-15T11:00:00', 2, 2),
       (7, 'Physical Therapy', '2024-01-01T09:00:00', 1, 1),
       (8, 'Cardiology Consultation', '2024-01-20T14:00:00', 3, 2),
       (9, 'Dermatology Checkup', '2024-02-01T10:00:00', 1, 1),
       (10, 'Neurology Consultation', '2024-02-15T11:00:00', 2, 2);

-- Przykładowe dane dla tabeli medical_treatment
insert into medical_treatment (id, description, type, visit_id)
values (1, 'Blood Test', 'RTG', 1),
       (2, 'Skin Biopsy', 'USG', 2);
