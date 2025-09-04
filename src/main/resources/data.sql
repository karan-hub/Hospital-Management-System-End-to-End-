-- ==========================
-- DOCTORS
-- ==========================
INSERT INTO doctor (doctor_id, first_name, last_name, specialization, contact_number, email)
VALUES (1, 'Amit', 'Shah', 'Cardiology', 9876543210, 'amit.shah@example.com');

INSERT INTO doctor (doctor_id, first_name, last_name, specialization, contact_number, email)
VALUES (2, 'Neha', 'Patel', 'Dermatology', 9876543211, 'neha.patel@example.com');

-- ==========================
-- DEPARTMENTS
-- ==========================
INSERT INTO department (department_id, name, description, head_doctor)
VALUES (1, 'Cardiology', 'Heart specialists', 1);

INSERT INTO department (department_id, name, description, head_doctor)
VALUES (2, 'Dermatology', 'Skin specialists', 2);

-- ==========================
-- PATIENTS
-- ==========================
INSERT INTO patient (patient_id, first_name, last_name, dob, gender, contact_number, email, address, blood_group)
VALUES (1, 'Rohit', 'Kumar', '1990-05-15', 'Male', '9123456780', 'rohit.kumar@example.com', 'Mumbai', 'A_POSITIVE');

INSERT INTO patient (patient_id, first_name, last_name, dob, gender, contact_number, email, address, blood_group)
VALUES (2, 'Pooja', 'Sharma', '1988-11-22', 'Female', '9123456781', 'pooja.sharma@example.com', 'Pune', 'O_NEGATIVE');

-- ==========================
-- INSURANCE
-- ==========================
INSERT INTO insurance (insurance_id, policyNumber, provider, validUntil, createdAt)
VALUES (1, 'POL12345', 'LIC', '2030-12-31', NOW());

INSERT INTO insurance (insurance_id, policyNumber, provider, validUntil, createdAt)
VALUES (2, 'POL67890', 'HDFC', '2028-05-20', NOW());

-- Link insurance to patients
UPDATE patient SET insurance_id = 1 WHERE patient_id = 1;
UPDATE patient SET insurance_id = 2 WHERE patient_id = 2;

-- ==========================
-- PAYMENTS
-- ==========================
INSERT INTO payment (bill_id, amount, status)
VALUES (1, 5000, 'BOOKED');

INSERT INTO payment (bill_id, amount, status)
VALUES (2, 3000, 'COMPLETED');

-- ==========================
-- PRESCRIPTIONS
-- ==========================
INSERT INTO prescription (prescription_id, medications, notes)
VALUES (1, 'Paracetamol 500mg', 'Take twice a day');

INSERT INTO prescription (prescription_id, medications, notes)
VALUES (2, 'Ibuprofen 400mg', 'Take after meals');

-- ==========================
-- APPOINTMENTS
-- ==========================
INSERT INTO appointment (appointment_id, start_time, end_time, status, payment_status, patient_id, doctor_id, payment_id, prescription_id)
VALUES (1, '2025-09-05 10:00:00', '2025-09-05 10:30:00', 'BOOKED', 'Completed', 1, 1, 1, 1);

INSERT INTO appointment (appointment_id, start_time, end_time, status, payment_status, patient_id, doctor_id, payment_id, prescription_id)
VALUES (2, '2025-09-05 11:00:00', '2025-09-05 11:30:00', 'COMPLETED', 'Completed', 2, 2, 2, 2);

-- ==========================
-- DOCTORS IN DEPARTMENTS (ManyToMany)
-- ==========================
INSERT INTO doctors_in_department (department_id, doctor_id) VALUES (1, 1);
INSERT INTO doctors_in_department (department_id, doctor_id) VALUES (2, 2);
