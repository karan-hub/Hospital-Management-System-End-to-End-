package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Insurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
//    @Query(value = "select * from Insurance", nativeQuery = true)
//    Page<Patient> findAllInsurance(Pageable pageable);
    Page<Insurance> findAll(Pageable pageable);
}