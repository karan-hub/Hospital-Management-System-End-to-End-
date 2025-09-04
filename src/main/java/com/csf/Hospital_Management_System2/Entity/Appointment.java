package com.csf.Hospital_Management_System2.Entity;

import com.csf.Hospital_Management_System2.Entity.type.PaymentStatus;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long appointmentId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status ;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "patient_id" , nullable = false )
    private Patient  patient;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id" , nullable = false)
    private Doctor doctor ;

    @JsonBackReference
    @OneToOne(orphanRemoval = true , fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @JsonBackReference
    @OneToOne(orphanRemoval = true , cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @JoinColumn(name = "prescription_id" , nullable = true)
    private  Prescription prescription;
}
