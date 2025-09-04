package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Dot.RequestInsuranceDTO;
import com.csf.Hospital_Management_System2.Dot.ResponceInsuranceDTO;
import com.csf.Hospital_Management_System2.Entity.Insurance;
import com.csf.Hospital_Management_System2.Entity.Patient;
import com.csf.Hospital_Management_System2.Repository.InsuranceRepository;
import com.csf.Hospital_Management_System2.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class InsuranceService {
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;


    @Transactional
    public void assignInsurance(Long patient_id , RequestInsuranceDTO dto){
        Patient patient = patientRepository
                .findById(patient_id)
                .orElseThrow(()->new RuntimeException("Patient Not Found "));

        Insurance insurance = Insurance.builder()
                .policyNumber(dto.getPolicyNumber())
                .provider(dto.getProvider())
                .validUntil(dto.getValidUntil())
                .patient(patient)
                .build();

        patient.setInsurance(insurance);
        patientRepository.save(patient);
        System.out.println("control" + patient_id);
    }

    public ResponceInsuranceDTO createInsurance(RequestInsuranceDTO  dto){
        Insurance insurance = Insurance.builder()
                .policyNumber(dto.getPolicyNumber())
                .provider(dto.getProvider())
                .validUntil(dto.getValidUntil())
                .build();
        insuranceRepository.save(insurance);
        return generateResponse(insurance);
    }

    public  ResponceInsuranceDTO generateResponse(Insurance insurance){
        ResponceInsuranceDTO responceInsuranceDTO = new ResponceInsuranceDTO();
        responceInsuranceDTO.setInsurance_id(insurance.getInsurance_id());
        responceInsuranceDTO.setPolicyNumber(insurance.getPolicyNumber());
        responceInsuranceDTO.setProvider(insurance.getProvider());
        responceInsuranceDTO.setValidUntil(insurance.getValidUntil());
        if (insurance.getPatient() != null)
            responceInsuranceDTO.setPatient(insurance.getPatient().getPatientId());

        return  responceInsuranceDTO;
    }

    @Transactional
    public  ResponceInsuranceDTO updateInsurance(Long insurance_id , RequestInsuranceDTO dto){
        Insurance currentInsurance =  insuranceRepository
                .findById(insurance_id)
                .orElseThrow(()->new RuntimeException("Insurance Not Found "));
        if (dto.getValidUntil() != null)
            currentInsurance.setValidUntil(dto.getValidUntil());
        if (dto.getProvider() != null)
            currentInsurance.setProvider(dto.getProvider());
        if (dto.getPatient() != null){
            Patient patient =  patientRepository.findById(dto.getPatient())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id " + dto.getPatient()));;
            patient.setInsurance(currentInsurance);
            patientRepository.save(patient);
        }
        return  generateResponse(currentInsurance);
    }

    public  ResponceInsuranceDTO getInsuranceById(Long insuranceId){
        Insurance insurance=  insuranceRepository
                .findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Insurance Not Found "));
        return  generateResponse(insurance);
    }

    public ResponceInsuranceDTO getInsuranceByPatientId(Long patientId){
         Patient patient = patientRepository.findById(patientId)
                 .orElseThrow(()->new RuntimeException("Patient Not Found "));
         return  generateResponse(patient.getInsurance());
    }

    @Transactional
    public void removeInsurance(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new RuntimeException("Patient Not Found "));
        patient.setInsurance(null);
    }

    public Optional<LocalDate> checkInsuranceValidity(Long patientId){
        return  patientRepository.findById(patientId)
                .map(Patient :: getInsurance)
                .map(Insurance :: getValidUntil);
    }

    public Page<Insurance> listAllInsurances(int page , int size){
        Pageable pageable = PageRequest.of(page , size , Sort.by("policyNumber").ascending());
        return  insuranceRepository.findAll(pageable);
    }






}
