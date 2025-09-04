package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Entity.Appointment;
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

    public void  createDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    public Doctor getDoctors(Long doctor_id){
         return doctorRepository.findById(doctor_id).orElseThrow(()-> new RuntimeException("Doctor Not Found"));
    }

    public List<Doctor> getDoctors( ){
        return doctorRepository.findAll();
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

    public void  updateDoctor(Long doctor_id , Doctor  newDetails){
      Doctor doctor =  doctorRepository
                .findById(doctor_id)
                .orElseThrow(()-> new RuntimeException("Doctor Not Found"));
        doctor.setFirst_name(newDetails.getFirst_name());
        doctor.setLast_name(newDetails.getLast_name());
        doctor.setSpecialization(newDetails.getSpecialization());
        doctor.setEmail(newDetails.getEmail());
        doctor.setDepartment(newDetails.getDepartment());

        doctorRepository.save(doctor);

    }

}
