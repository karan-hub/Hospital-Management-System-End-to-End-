package com.csf.Hospital_Management_System2.Dot;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseAppointmentDTO {
    private Long appointmentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String paymentStatus;
    private Long patientId;
    private Long doctorId;
    private Long prescriptionId;
}
