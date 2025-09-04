package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Dot.ResponseAppointmentDTO;
import com.csf.Hospital_Management_System2.Dot.updateAppointmentDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import com.csf.Hospital_Management_System2.Entity.Patient;
import com.csf.Hospital_Management_System2.Entity.Prescription;
import com.csf.Hospital_Management_System2.Entity.type.PaymentStatus;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.csf.Hospital_Management_System2.Repository.AppointmentRepository;
import com.csf.Hospital_Management_System2.Repository.DoctorRepository;
import com.csf.Hospital_Management_System2.Repository.PatientRepository;
import com.csf.Hospital_Management_System2.securityDetail.exception.AppointmenNotFound;
import com.csf.Hospital_Management_System2.securityDetail.exception.PatientNotFoundException;
import com.csf.Hospital_Management_System2.securityDetail.exception.SlotBookedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import  com.csf.Hospital_Management_System2.dto.CreateAppointmentDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private  final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;



    public ResponseAppointmentDTO createAppointment( CreateAppointmentDTO dto ) {
        Long  doctorId =dto.getDoctorId();
        Long  patientId =dto.getPatientId();
        boolean isBooked = appointmentRepository
                .existsByDoctorDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        doctorId,
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if (isBooked) {
            throw new SlotBookedException("Slot already booked for this doctor");
        }

        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(()->new PatientNotFoundException("Patient NOT Found"));

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(()->new PatientNotFoundException("Doctor NOT Found"));

        Appointment appointment = Appointment.builder()
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .status(Status.BOOKED)
                .paymentStatus(PaymentStatus.Incompleted)
                .patient(patient)
                .doctor(doctor)
                .build();

        patient.addAppointment(appointment);
        doctor.getAppointments().add(appointment);
        appointmentRepository.save(appointment);
        return  generateResponse(appointment);
    }

    public ResponseAppointmentDTO getAppointmentById(Long id){
       Optional<Appointment> appointment =  appointmentRepository.findById(id);
       if (appointment.isEmpty())
           throw  new RuntimeException("Appointment Not Found");

       return generateResponse(appointment.get());
   };

    private  ResponseAppointmentDTO generateResponse(Appointment response){
        ResponseAppointmentDTO responseAppointmentDTO = new ResponseAppointmentDTO();
        responseAppointmentDTO.setAppointmentId(response.getAppointmentId());
        responseAppointmentDTO.setStartTime(response.getStartTime());
        responseAppointmentDTO.setEndTime(response.getEndTime());
        responseAppointmentDTO.setStatus(response.getStatus().name());
        responseAppointmentDTO.setPaymentStatus(response.getPaymentStatus().name());
        responseAppointmentDTO.setPatientId(response.getPatient().getPatientId());
        responseAppointmentDTO.setDoctorId(response.getDoctor().getDoctorId());
        responseAppointmentDTO.setPrescriptionId(
                response.getPrescription() != null ? response.getPrescription().getPrescription_id() : null
        );
        return responseAppointmentDTO;
    }

    public  List<ResponseAppointmentDTO> getAllAppointments(){
        return appointmentRepository.findAll()
                .stream()
                .map(this::generateResponse)
                .toList();
    }

    public List<ResponseAppointmentDTO> getAppointmentsByPatient(Long patientId) {
        return  appointmentRepository.findByPatientPatientId(patientId)
                .stream()
                .map(this::generateResponse)
                .toList();
    }

    public  List<ResponseAppointmentDTO> getAppointmentsByDoctor(Long doctorId){
        return  appointmentRepository.findByDoctorDoctorId(doctorId)
                .stream()
                .map(this::generateResponse)
                .toList();
    };

    public ResponseAppointmentDTO updateAppointment(Long appointmentId, updateAppointmentDTO dto) {
        Long doctorId = dto.getDoctorId();
        Long patientId = dto.getPatientId();

        boolean isBooked = appointmentRepository
                .existsByDoctorDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        doctorId,
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if (isBooked)
            throw new SlotBookedException("Slot already booked for this doctor");

        Appointment selectedAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmenNotFound("Appointment Not Found for id " + appointmentId));


        selectedAppointment.setStartTime(dto.getStartTime());
        selectedAppointment.setEndTime(dto.getEndTime());

        if (dto.getStatus() != null )
            selectedAppointment.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));

        if (dto.getPaymentStatus() != null )
            selectedAppointment.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + patientId));

        selectedAppointment.setDoctor(doctor);
        selectedAppointment.setPatient(patient);

        if (dto.getPresciption() != null) {
            Prescription prescription = new Prescription();
            prescription.setNotes(dto.getPresciption());
            selectedAppointment.setPrescription(prescription);
        }

        appointmentRepository.save(selectedAppointment);
        return generateResponse(selectedAppointment);
    }

    public  Status cancelAppointment(Long patient_id  , Long appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentIdAndPatientPatientId( appointmentId , patient_id)
                .orElseThrow(() -> new AppointmenNotFound(
                        "Appointment not found for patientId " + patient_id + " and appointmentId " + appointmentId));
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
        return appointment.getStatus();
    }

    public ResponseAppointmentDTO updateStatus(Long patientId ,Long appointmentId, String  status) {
        Appointment appointment = appointmentRepository.findByAppointmentIdAndPatientPatientId(appointmentId, patientId)
                .orElseThrow(() -> new AppointmenNotFound(
                        "Appointment not found for patientId " + patientId + " and appointmentId " + appointmentId));

        Status finalStatus = Arrays.stream(Status.values())
                        .filter(x-> x.name()
                        .equalsIgnoreCase(status))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("\n Invalid status: " + status));

        appointment.setStatus(finalStatus);
         appointmentRepository.save(appointment);
       return  generateResponse(appointment);
    }


}
