package com.csf.Hospital_Management_System2.Entity;

import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Long bill_id ;

    @JsonManagedReference
    @OneToOne(mappedBy = "payment" ,  cascade =  CascadeType.ALL)
    private Appointment appointment ;

    private  Long amount ;

    @Enumerated(EnumType.STRING)
    private Status status ;

}
