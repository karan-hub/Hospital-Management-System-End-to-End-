package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}