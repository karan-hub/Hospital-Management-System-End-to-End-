package com.csf.Hospital_Management_System2.Controller;

import com.csf.Hospital_Management_System2.Dot.ApiResponse;
import com.csf.Hospital_Management_System2.Dot.HeadDoctorUpdateRequest;
import com.csf.Hospital_Management_System2.Dot.ResponceDeprecatedDTO;
import com.csf.Hospital_Management_System2.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private  final DepartmentService departmentService ;
    @GetMapping
    public ResponseEntity<ApiResponse> getAllDepartment(){
        List<ResponceDeprecatedDTO> departments =  departmentService.getAllDepartment();
              if (!departments.isEmpty())
                  return  ResponseEntity.ok(new ApiResponse<>( true ,"Some Departments Found " , departments));

              return  ResponseEntity.ok(new ApiResponse<>( false ," Departments Not Found ! " , null));

    }

    @PatchMapping("/{id}/head-doctor")
     public ResponseEntity<ApiResponse> setHeadDoctor(@RequestBody HeadDoctorUpdateRequest dto ,
                                                             @PathVariable Long id){
        try {
            ResponceDeprecatedDTO updated = departmentService.setHeadDoctor(id,  dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Head doctor updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Invalid Details", null));
         }

     }

     @GetMapping("{id}")
     public ResponseEntity<ApiResponse>  getDepartmentsByDoctorId(@PathVariable Long id){
         List<ResponceDeprecatedDTO> responce = departmentService.getDepartmentsByDoctorId(id);
        if (!responce.isEmpty())
            return  ResponseEntity.ok(new ApiResponse(true , "Department Found" , responce));

        return  ResponseEntity.ok(new ApiResponse(true , " Not Found" , null));

     }

     @PostMapping("/add/{doctorId}")
     public  ResponseEntity addDoctor(@PathVariable Long doctorId ,
                                      @RequestParam Long departmentId){
        try {
            departmentService.addDoctorToDepartment(doctorId,departmentId);
            return  ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
             return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }

     }



    @DeleteMapping("/remove/{doctorId}")
    public  ResponseEntity  removeDoctor(@PathVariable Long doctorId ,
                                     @RequestParam String departmentname){
        try {
            departmentService.removeDoctorFromDepartment(doctorId, departmentname);
            return  ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }

    }



}
