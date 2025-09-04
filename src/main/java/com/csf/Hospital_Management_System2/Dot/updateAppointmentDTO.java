package com.csf.Hospital_Management_System2.Dot;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class updateAppointmentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long doctorId;
    private Long patientId;
    private String status;
    private String paymentStatus;
    private String presciption;
}
