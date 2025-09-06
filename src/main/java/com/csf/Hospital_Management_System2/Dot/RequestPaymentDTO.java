package com.csf.Hospital_Management_System2.Dot;


import lombok.Data;

@Data
public class RequestPaymentDTO {
    private Long appointmentId ;
    private  Long amount ;
    private String status ;
}
