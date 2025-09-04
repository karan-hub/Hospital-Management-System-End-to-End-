package com.csf.Hospital_Management_System2.Controller;

import com.csf.Hospital_Management_System2.Dot.RequestInsuranceDTO;
import com.csf.Hospital_Management_System2.Dot.ResponceInsuranceDTO;
import com.csf.Hospital_Management_System2.Entity.Insurance;
import com.csf.Hospital_Management_System2.Service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

   private final InsuranceService insuranceService;

   @PostMapping("/assign/{patientsId}")
   @ResponseStatus(HttpStatus.OK)
   public  void  getInsurance(@RequestBody RequestInsuranceDTO dto,
                              @PathVariable Long patientsId){
       System.out.println("control" + patientsId);
       insuranceService.assignInsurance(patientsId,dto);
   }
    @PostMapping("/create")
    public ResponceInsuranceDTO createInsurance(@RequestBody RequestInsuranceDTO dto){
       return  insuranceService.createInsurance(dto);
    }

    @PostMapping("/update/{insuranceId}")
    public ResponceInsuranceDTO updateInsurance(@RequestBody RequestInsuranceDTO dto,
                                                @PathVariable Long insuranceId){
       return  insuranceService.updateInsurance( insuranceId , dto);
    }

    @GetMapping("/{id}")
    public ResponceInsuranceDTO getInsuranceById(@PathVariable Long id){
        return  insuranceService.getInsuranceById(id);
    }

    @GetMapping("/patient/{id}")
    public ResponceInsuranceDTO getInsuranceByPatientId(@PathVariable Long id){
        return  insuranceService.getInsuranceByPatientId(id);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<LocalDate> checkInsuranceValidity(@PathVariable Long id){
        return insuranceService.checkInsuranceValidity(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping()
    public ResponseEntity<Page<Insurance>> getAllInsurances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Insurance> insurances = insuranceService.listAllInsurances(page, size);
        return ResponseEntity.ok(insurances);
    }



}
