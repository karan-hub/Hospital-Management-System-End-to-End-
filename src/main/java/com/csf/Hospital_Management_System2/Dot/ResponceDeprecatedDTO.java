package com.csf.Hospital_Management_System2.Dot;

import com.csf.Hospital_Management_System2.Entity.Doctor;
import lombok.Data;
import java.util.List;

@Data
public class ResponceDeprecatedDTO {
    private  Long department_id;
    private  String name ;
    private  String  description;
    private String head;
    private List<String> allDoctors  ;
}


