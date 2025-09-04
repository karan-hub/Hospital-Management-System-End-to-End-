package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Payment;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(Status status);
}