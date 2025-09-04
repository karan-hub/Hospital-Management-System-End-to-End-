package com.csf.Hospital_Management_System2.Dot;

import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Insurance;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestPatientDTO {

    private  String first_name;

    private  String last_name ;

    private String dob ;

    private   String gender ;

    private   String contact_number ;

    private   String email ;

    private   String address;

    private String bloodGroup;

}
