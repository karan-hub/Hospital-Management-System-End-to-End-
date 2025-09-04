package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Dot.RequestPatientDTO;
import com.csf.Hospital_Management_System2.Dot.ResponsePatientDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Patient;
import com.csf.Hospital_Management_System2.Entity.type.BloodGroup;
import com.csf.Hospital_Management_System2.Repository.PatientRepository;
import com.csf.Hospital_Management_System2.securityDetail.exception.PatientNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PatientService {

    private  final  PatientRepository patientRepository;

    @Transactional
   public ResponsePatientDTO createPatient(RequestPatientDTO dto){

      Optional<BloodGroup> bloodGroup= Arrays.stream(BloodGroup.values())
               .filter(
                       x->x.name().equalsIgnoreCase(dto.getBloodGroup())
               ).findFirst();

       Patient patient = Patient.builder()
               .first_name(dto.getFirst_name())
               .last_name(dto.getLast_name())
               .dob(LocalDate.parse(dto.getDob()))
               .gender(dto.getGender())
               .contact_number (dto.getContact_number ())
               .email(dto.getEmail())
               .address(dto.getAddress())
               .bloodGroup(bloodGroup.orElse(null))
           .build();
       patientRepository.save(patient);
        return generateResponse(patient);
    };

   private static ResponsePatientDTO generateResponse(Patient patient){
       ResponsePatientDTO dto = new ResponsePatientDTO();
       String policy = (patient.getInsurance() !=null) ? patient.getInsurance().getPolicyNumber():null;
       dto.setPatientId(patient.getPatientId());
       dto.setFirst_name(patient.getFirst_name());
       dto.setLast_name(patient.getLast_name());
       dto.setDob(patient.getDob().toString());
       dto.setGender(patient.getGender());
       dto.setContact_number (patient.getContact_number ());
       dto.setEmail(patient.getEmail());
       dto.setAddress(patient.getAddress());
       dto.setBloodGroup(patient.getBloodGroup().toString());
       dto.setInsurance(policy);
       return dto;
   }

   public  ResponsePatientDTO getPatientById(Long id){
        Patient patient= patientRepository
                .findById(id).orElseThrow(()->new PatientNotFoundException("USER NOT Found"));
        return generateResponse(patient);
    };

   public List<ResponsePatientDTO> getAllPatients(){
        return patientRepository.findAll()
                .stream().map(PatientService::generateResponse)
                .toList();
    };

   public List<Appointment> getPatientAllAppointment(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getAppointments();
    };

   public  ResponsePatientDTO updatePatient(Long id,RequestPatientDTO dto){
       Patient existing = patientRepository
               .findById(id)
               .orElseThrow(()->new PatientNotFoundException("USER NOT Found"));
       Optional<BloodGroup> bloodGroup = Arrays.stream(BloodGroup.values())
                        .filter(x->x.name().equalsIgnoreCase(dto.getBloodGroup()))
                        .findFirst();

        existing.setFirst_name(dto.getFirst_name());
        existing.setLast_name(dto.getLast_name());
        existing.setEmail(dto.getEmail());
        existing.setContact_number(dto.getContact_number());
        existing.setAddress(dto.getAddress());
        existing.setBloodGroup(bloodGroup.orElseThrow(
                ()-> new RuntimeException("bloodGroup not found")
        ));
        Patient patient= patientRepository.save(existing);
        return generateResponse(patient);
    };

   public void deletePatient(Long id){
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER NOT Found"));
        patientRepository.delete(existing);
    }



}
