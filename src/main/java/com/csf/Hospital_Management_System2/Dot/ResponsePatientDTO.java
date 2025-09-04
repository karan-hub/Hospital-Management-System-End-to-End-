package com.csf.Hospital_Management_System2.Dot;

import lombok.Data;


@Data
public class ResponsePatientDTO {

    private  Long patientId ;

    private  String first_name;

    private  String last_name ;

    private  String dob ;

    private  String gender ;

    private  String contact_number ;

    private  String email ;

    private  String address;

    private  String bloodGroup;

    private  String insurance;


}
