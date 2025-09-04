package com.csf.Hospital_Management_System2.Entity;

import com.csf.Hospital_Management_System2.Entity.type.BloodGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long patientId ;

    @Column(nullable = false)
    private  String first_name;

    @Column(nullable = false)
    private  String last_name ;

    @Column(nullable = false)
    private   LocalDate dob ;

    @Column(nullable = false)
    private   String gender ;

    @Column(nullable = false  , unique = true)
    private   String contact_number ;

    @Column(nullable = false , unique = true )
    private   String email ;

    @Column(nullable = false , length = 100)
    private   String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;



    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "insurance_id")
    private  Insurance insurance;

    @JsonManagedReference
    @OneToMany(mappedBy = "patient" , cascade =  CascadeType.ALL, fetch = FetchType.LAZY , orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setPatient(this);
    }

     public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setPatient(null);
    }


}
