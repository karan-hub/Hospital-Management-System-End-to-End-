package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Dot.RequestDoctorDTO;
import com.csf.Hospital_Management_System2.Dot.ResponseDoctorDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Department;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import com.csf.Hospital_Management_System2.Repository.AppointmentRepository;
import com.csf.Hospital_Management_System2.Repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {


    private final DoctorRepository doctorRepository;
    private   final AppointmentRepository appointmentRepository;

    public ResponseDoctorDTO createDoctor(RequestDoctorDTO dto){
        Doctor doctor = Doctor.builder()
                .first_name(dto.getFirst_name())
                .last_name(dto.getLast_name())
                .specialization(dto.getSpecialization())
                .contact_number(dto.getContact_number())
                .email(dto.getEmail())
                .build();
            Doctor newDoctor = doctorRepository.save(doctor);
            return  generateResponse(newDoctor) ;
    }
    public static ResponseDoctorDTO generateResponse(Doctor doctor) {
        ResponseDoctorDTO response = new ResponseDoctorDTO();
        response.setFirst_name(doctor.getFirst_name());
        response.setLast_name(doctor.getLast_name());
        response.setSpecialization(doctor.getSpecialization());
        response.setContact_number(doctor.getContact_number());
        response.setEmail(doctor.getEmail());
        Department  department=   doctor.getDepartment();
        response.setDepartment(
                department != null ? department.getName() : " Till Aot Assigned "
        );
        return response;
    }

    public ResponseDoctorDTO getDoctor(Long doctor_id){
         Doctor doctor = doctorRepository.findById(doctor_id)
                 .orElseThrow(()-> new RuntimeException("Doctor Not Found"));
         return  generateResponse(doctor);
    }

    public List<ResponseDoctorDTO> getDoctors(){
        return doctorRepository.findAll().
                stream().map(DoctorService::generateResponse).toList();
    }

    public void deleteDoctor( Long doctor_id){
       Doctor doctor = doctorRepository
               .findById(doctor_id)
               .orElseThrow(()-> new RuntimeException("Doctor Not Found"));
       doctorRepository.delete(doctor);
    }

    public List<Appointment> getAllAppointments(Long  doctor_id){
        doctorRepository
                .findById(doctor_id)
                .orElseThrow(()-> new RuntimeException("Doctor Not Found"));

        return appointmentRepository.findByDoctorDoctorId(doctor_id);
    }

    public ResponseDoctorDTO  updateDoctor(Long doctor_id , RequestDoctorDTO  doctorDTO){
      Doctor doctor =  doctorRepository
                .findById(doctor_id)
                .orElseThrow(()-> new RuntimeException("Doctor Not Found"));
        doctor.setFirst_name(doctorDTO.getFirst_name());
        doctor.setLast_name(doctorDTO.getLast_name());
         doctor.setContact_number(doctorDTO.getContact_number());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setEmail(doctorDTO.getEmail());


        Doctor updatedDoctor = doctorRepository.save(doctor);
        return generateResponse(updatedDoctor);

    }

}
