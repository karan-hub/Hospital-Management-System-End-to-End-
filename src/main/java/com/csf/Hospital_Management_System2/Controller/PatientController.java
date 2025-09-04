package com.csf.Hospital_Management_System2.Controller;

import com.csf.Hospital_Management_System2.Dot.ApiResponse;
import com.csf.Hospital_Management_System2.Dot.RequestPatientDTO;
import com.csf.Hospital_Management_System2.Dot.ResponsePatientDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private  final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<ResponsePatientDTO> createPatient(@RequestBody RequestPatientDTO dto){
         ResponsePatientDTO responsePatientDTO= patientService.createPatient(dto);
         return ResponseEntity.ok(responsePatientDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPatientById(@PathVariable Long id){
        try {
            ResponsePatientDTO response = patientService.getPatientById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Patient created successfully", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, " No one Patient fond for this ID ", null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllPatients(){
        try {
            List<ResponsePatientDTO> response = patientService.getAllPatients();
            return ResponseEntity.ok(new ApiResponse<>(true, "Patient created successfully", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, " No one Patient fond for this ID ", null));
        }
    }

    @GetMapping("appointments/{id}")
    public ResponseEntity<ApiResponse> getAllPatients(@PathVariable Long id){
        try {
            List<Appointment> response = patientService.getPatientAllAppointment(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Patient created successfully", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, " No one Patient fond for this ID ", null));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> getUpdatePatientById(@PathVariable Long id , @RequestBody RequestPatientDTO dto){
        try {
            ResponsePatientDTO response = patientService.updatePatient(id , dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Patient Updated successfully", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, " No one Patient fond for this ID ", null));
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Long id){
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Patient Deleted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage() , null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, " No one Patient fond for this ID ", null ) );
        }
    }

}
