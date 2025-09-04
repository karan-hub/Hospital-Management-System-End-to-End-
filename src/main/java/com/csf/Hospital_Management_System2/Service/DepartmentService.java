package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Entity.Department;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import com.csf.Hospital_Management_System2.Repository.DepartmentRepository;
import com.csf.Hospital_Management_System2.Repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private   final DepartmentRepository departmentRepository ;
    private  final DoctorRepository doctorRepository;

    public Department setHeadDoctor(Doctor doctor , String  department_name ){
        Department department =  departmentRepository
                .findByName(department_name)
                .orElseThrow(()->new RuntimeException("No"));

        department.setDoctor(doctor);
        return  departmentRepository.save(department);
    }

    public List<Department> getAllDepartment(){
        return  departmentRepository.findAll();
    }

    public List<Department> getDepartmentsByDoctorId(Long doctorId) {
        return departmentRepository.findAllByDoctorId(doctorId);
    }

    public  void addDoctorToDepartment(Long doctorId , Long department_id ) {
        Department department = departmentRepository.findById(department_id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        department.getDoctors().add(doctor);
        doctor.getDepartments().add(department);

        departmentRepository.save(department);
        doctorRepository.save(doctor);

    }

    public  void removeDoctorFromDepartment(Long doctorId , String department_name ) {
        Department department = departmentRepository.findByName(department_name)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        department.getDoctors().remove(doctor);
        doctor.getDepartments().remove(department);

        departmentRepository.save(department);
        doctorRepository.save(doctor);

    }

}
