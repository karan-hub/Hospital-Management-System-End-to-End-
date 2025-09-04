package com.csf.Hospital_Management_System2.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long doctorId ;

    @Column(nullable = false)
    private  String first_name;

    @Column(nullable = false)
    private  String last_name ;

    @Column(nullable = false)
    private  String specialization;

    @Column(nullable = false , length = 10 , unique = true)
    private   Integer contact_number ;

    @Column(nullable = false , unique = true )
    private   String email ;

    @JsonManagedReference
    @OneToMany(mappedBy ="doctor" , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDoctor(this);
    }
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setDoctor(null);
    }

    @OneToOne(mappedBy = "doctor")
    private Department department;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();

    public void addDepartment(Department department) {
        departments.add(department);
        if (!department.getDoctors().contains(this)) {
            department.getDoctors().add(this);
        }
    }
    public void removeDepartment(Department department) {
        departments.remove(department);
        department.getDoctors().remove(this);
    }
}
