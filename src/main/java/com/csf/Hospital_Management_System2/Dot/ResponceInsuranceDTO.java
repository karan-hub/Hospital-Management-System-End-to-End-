package com.csf.Hospital_Management_System2.Dot;

import com.csf.Hospital_Management_System2.Entity.Patient;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ResponceInsuranceDTO {
    private  Long  insurance_id;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
    private Long patient;
}
