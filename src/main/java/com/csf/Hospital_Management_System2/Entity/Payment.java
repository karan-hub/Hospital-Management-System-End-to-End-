package com.csf.Hospital_Management_System2.Entity;

import com.csf.Hospital_Management_System2.Entity.type.PaymentStatus;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private   Long  bill_id ;

    @JsonManagedReference
    @OneToOne(mappedBy = "payment" ,  cascade =  CascadeType.ALL)
    private Appointment appointment ;

    private  Long amount ;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status ;

}
