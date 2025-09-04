package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}