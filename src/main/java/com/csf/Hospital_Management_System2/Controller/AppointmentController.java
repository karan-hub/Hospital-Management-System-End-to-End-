package com.csf.Hospital_Management_System2.Controller;


import com.csf.Hospital_Management_System2.Dot.ResponseAppointmentDTO;
import com.csf.Hospital_Management_System2.Dot.updateAppointmentDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.csf.Hospital_Management_System2.Service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  com.csf.Hospital_Management_System2.dto.CreateAppointmentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private  final AppointmentService appointmentService;

//    get All appointments
    @GetMapping
    public List<ResponseAppointmentDTO> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

//    get By ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDTO> getAppointmentByID(@PathVariable Long id){
       try{
           ResponseAppointmentDTO responseAppointmentDTO = appointmentService.getAppointmentById(id);

           return  ResponseEntity.ok(responseAppointmentDTO);
       }
       catch (RuntimeException e ){
           return  ResponseEntity.notFound().build();
       }
    }

//    Get Appointments by Patient ID
    @GetMapping("/patient/{patientId}")
    public List<ResponseAppointmentDTO> getAllAppointmentsByPatientId(@PathVariable Long patientId){
        return  appointmentService.getAppointmentsByPatient(patientId);
    }

//    Get appointments by doctor
    @GetMapping("/doctor/{doctorId}")
    public List<ResponseAppointmentDTO> getAllAppointmentsByDoctorId(@PathVariable Long  doctorId){
        return  appointmentService.getAppointmentsByDoctor(doctorId);
    }

//    Create Appointment
    @PostMapping("/create")
    public ResponseAppointmentDTO createAppointment(@RequestBody  CreateAppointmentDTO createAppointmentDTO){
        return appointmentService.createAppointment(createAppointmentDTO);
    }

//    update Appointment
    @PostMapping("/update/{appointmentId}")
    public ResponseAppointmentDTO updateAppointment(@RequestBody updateAppointmentDTO dto,
                                         @PathVariable Long  appointmentId){

       return appointmentService.updateAppointment(appointmentId ,dto);
    }

//    Cancel Appointment
    @PostMapping("/cancel/{patientId}/{appointmentId}")
    public  ResponseEntity<Status> cancelAppointment(@PathVariable Long patientId ,
                                                     @PathVariable Long appointmentId){
        Status status = appointmentService.cancelAppointment(patientId,appointmentId);
        return  ResponseEntity.ok(status);
    }

//
    @PutMapping("/status/{patientId}/{appointmentId}")
    public ResponseAppointmentDTO updateStatus(@PathVariable Long patientId,
                                    @PathVariable Long appointmentId,
                                    @RequestParam String status) {
    return appointmentService.updateStatus(patientId, appointmentId, status);
}

}
