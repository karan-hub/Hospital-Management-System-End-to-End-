package com.csf.Hospital_Management_System2.Dot;

import lombok.Data;

@Data
public class RequestDoctorDTO {
     private String first_name;
     private String last_name ;
     private String specialization;
     private Long contact_number ;
     private String email ;
     private String department;
}
