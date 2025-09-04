package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

 boolean existsByDoctorDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(Long doctorId, LocalDateTime endTime, LocalDateTime startTime);
     long countByDoctor_DoctorId(Long doctorId);
     long countByPatient_PatientId(Long patientId);
     List<Appointment> findByDoctor_DoctorIdAndStartTimeBetween(
            Long doctorId,
            LocalDateTime start,
            LocalDateTime end
    );
    Optional<Appointment> findByAppointmentIdAndPatientPatientId(Long appointmentId, Long patientId);
    List<Appointment> findByDoctorDoctorId(Long doctorId);
    List<Appointment> findByPatientPatientId(Long patientId);

}