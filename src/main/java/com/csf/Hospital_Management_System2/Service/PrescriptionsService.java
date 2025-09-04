package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Prescription;
import com.csf.Hospital_Management_System2.Repository.AppointmentRepository;
import com.csf.Hospital_Management_System2.Repository.PrescriptionRepository;
import com.csf.Hospital_Management_System2.securityDetail.exception.AppointmenNotFound;
import com.csf.Hospital_Management_System2.securityDetail.exception.PrescriptionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionsService {

    private   final PrescriptionRepository prescriptionRepository;
    private   final AppointmentRepository appointmentRepository;



//    GET /appointments/{appointmentId}/prescription — fetch
//    PUT /appointments/{appointmentId}/prescription — update (e.g., medications JSON, notes)
//    DELETE /appointments/{appointmentId}/prescription — remove (orphanRemoval will delete row)

    public Prescription getPrescription(Appointment appointment) {
        if (appointment.getPrescription() == null) {
            throw new PrescriptionNotFoundException("No prescription found for this appointment");
        }
        return appointment.getPrescription();
    }
    public void updatePrescription(Long appointment_id , Prescription newPrescription ) {
        Appointment appointment = appointmentRepository
                .findById(appointment_id)
                .orElseThrow(() -> new AppointmenNotFound("Appointment Not Found For This Id " + appointment_id));
        Prescription existing = appointment.getPrescription();
        if (existing != null) {
            existing.setMedications(newPrescription.getMedications());
            existing.setNotes(newPrescription.getNotes());
        }
        else
            appointment.setPrescription(newPrescription);
        appointmentRepository.save(appointment);
    }
    @Transactional
    public void deletePrescription(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmenNotFound("Appointment not found with id " + appointmentId));

        if (appointment.getPrescription() == null) {
            throw new RuntimeException("No prescription found for this appointment");
        }

         appointment.setPrescription(null);

        appointmentRepository.save(appointment);
    }




}
