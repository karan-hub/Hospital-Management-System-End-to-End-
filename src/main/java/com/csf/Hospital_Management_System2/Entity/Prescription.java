package com.csf.Hospital_Management_System2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long prescription_id ;

    @JsonManagedReference
    @Column(columnDefinition = "TEXT")
    private String medications;

    @Column(length = 500)
    private String notes;

    @OneToOne(mappedBy = "prescription"  )
    private  Appointment appointment  ;


}
