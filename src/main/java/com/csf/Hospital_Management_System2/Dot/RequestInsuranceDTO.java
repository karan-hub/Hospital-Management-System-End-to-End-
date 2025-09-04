package com.csf.Hospital_Management_System2.Dot;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RequestInsuranceDTO {
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
    private Long patient;
}

