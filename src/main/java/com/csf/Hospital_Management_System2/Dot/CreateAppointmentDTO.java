package com.csf.Hospital_Management_System2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long patientId;
    private Long doctorId;

}
