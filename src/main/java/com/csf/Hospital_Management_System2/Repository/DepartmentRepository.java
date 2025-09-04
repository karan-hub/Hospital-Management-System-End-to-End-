package com.csf.Hospital_Management_System2.Repository;

import com.csf.Hospital_Management_System2.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    @Query("SELECT dept FROM Department dept JOIN dept.doctors d WHERE d.id = :doctorId")
    List<Department> findAllByDoctorId(@Param("doctorId") Long doctorId);
}