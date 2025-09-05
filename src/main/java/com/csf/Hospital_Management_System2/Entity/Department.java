package com.csf.Hospital_Management_System2.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Department {
    @Id
    private  Long department_id;
    private  String name ;
    private  String  description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_doctor"  )
    private Doctor doctor;

    @ManyToMany
    @JoinTable(
            name = "doctors_in_department" ,
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns =@JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors = new ArrayList<>();


}
