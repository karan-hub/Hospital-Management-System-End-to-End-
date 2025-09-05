package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Dot.HeadDoctorUpdateRequest;
import com.csf.Hospital_Management_System2.Dot.ResponceDeprecatedDTO;
import com.csf.Hospital_Management_System2.Entity.Department;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import com.csf.Hospital_Management_System2.Repository.DepartmentRepository;
import com.csf.Hospital_Management_System2.Repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private   final DepartmentRepository departmentRepository ;
    private  final DoctorRepository doctorRepository;

    public static ResponceDeprecatedDTO generateResponse(Department department){
        ResponceDeprecatedDTO dto =  new ResponceDeprecatedDTO();
        dto.setDepartment_id(department.getDepartment_id());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        Doctor doctor = department.getDoctor();
        dto.setHead(
                doctor != null ? doctor.getFirst_name()+" "+doctor.getLast_name() : "Head Of Department  Not Found"
        );
        List<Doctor> doctorList = department.getDoctors();
        dto.setAllDoctors(
                doctorList.stream().map(doctor1 -> {
                            return doctor1.getFirst_name() + " " + doctor1.getLast_name();
                        })
                        .toList()
        );
        return dto;
    }


    public ResponceDeprecatedDTO setHeadDoctor(Long id , HeadDoctorUpdateRequest name ){
        Department department =  departmentRepository
                .findByName(name.getName())
                .orElseThrow(()->new RuntimeException("Not Found"));
        Doctor doctor =  doctorRepository.findById(id).orElseThrow(()->new RuntimeException(" Doctor Not found"));
        department.setDoctor(doctor);
        Department result = departmentRepository.save(department);
        return generateResponse(result);
    }

    public List<ResponceDeprecatedDTO> getAllDepartment(){
        return  departmentRepository.findAll()
                .stream().map(DepartmentService::generateResponse).toList();
    }

    public List<ResponceDeprecatedDTO> getDepartmentsByDoctorId(Long doctorId) {
        return departmentRepository.findAllByDoctorId(doctorId).stream()
                .map(DepartmentService ::generateResponse).toList();
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
