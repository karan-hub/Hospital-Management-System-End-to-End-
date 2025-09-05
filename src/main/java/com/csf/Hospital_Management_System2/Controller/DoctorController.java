package com.csf.Hospital_Management_System2.Controller;

import com.csf.Hospital_Management_System2.Dot.ApiResponse;
import com.csf.Hospital_Management_System2.Dot.RequestDoctorDTO;
import com.csf.Hospital_Management_System2.Dot.ResponseDoctorDTO;
import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Doctor;
import com.csf.Hospital_Management_System2.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private  final DoctorService  doctorService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>  createDoctor(@RequestBody RequestDoctorDTO doctorDTO){
      try {
          ResponseDoctorDTO doctor = doctorService.createDoctor(doctorDTO);
          return ResponseEntity.ok(new ApiResponse<>(true ,"successfully Doctor Created" , doctor));
      }catch(Exception e){
              return ResponseEntity.ok(new ApiResponse(false , "fail to create doctor" ,null));
    }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse> getDoctor(@PathVariable Long id){
        try {
             ResponseDoctorDTO doctorDTO = doctorService.getDoctor(id);
             return  ResponseEntity.ok(new ApiResponse(true ,"Doctor Found" , doctorDTO ));
        }catch(Exception ignored){
            return  ResponseEntity.ok(new ApiResponse(false ,"Not Found" , null ));
        }



    }

    @GetMapping
    public  ResponseEntity<ApiResponse> getAllDoctor(){
        try{
            List<ResponseDoctorDTO> doctors =  doctorService.getDoctors();
            return  ResponseEntity.ok(new ApiResponse(true ,"Some Doctor are Found" , doctors));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false ,"Not One Doctor Find" ,null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDoctor(@PathVariable Long id){
        try{
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok(new ApiResponse(true ,"successfully deleted" ,null));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false, "doctor Not fond", null));
        }
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<ApiResponse> getAllAppointments(@PathVariable Long id){
        try{
            List<Appointment> appointments =doctorService.getAllAppointments(id);
            return ResponseEntity.ok(new ApiResponse(true ,"these are All Appointment" , appointments));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false , "Appointment not Found" , null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateDoctor(@PathVariable Long id , @RequestBody RequestDoctorDTO dto){
        try{
            ResponseDoctorDTO doctorDTO = doctorService.updateDoctor(id ,dto );
            return  ResponseEntity.ok(new ApiResponse(true , "Successfully updated", doctorDTO ));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false ,"Not doctor found for this" , null));
        }
    }

}
