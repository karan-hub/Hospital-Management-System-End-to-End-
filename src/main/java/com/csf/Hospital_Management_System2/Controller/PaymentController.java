package com.csf.Hospital_Management_System2.Controller;

import com.csf.Hospital_Management_System2.Dot.ApiResponse;
import com.csf.Hospital_Management_System2.Dot.AssignRequestDTO;
import com.csf.Hospital_Management_System2.Dot.RequestPaymentDTO;
import com.csf.Hospital_Management_System2.Entity.Payment;
import com.csf.Hospital_Management_System2.Entity.type.PaymentStatus;
import com.csf.Hospital_Management_System2.Service.AppointmentService;
import com.csf.Hospital_Management_System2.Service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private  final PaymentsService paymentsService ;
    private  final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>  createPayment(@RequestBody RequestPaymentDTO dto){
        try{
            Payment payment = paymentsService.createPayment(dto);
            return  ResponseEntity.ok(new ApiResponse(true ,"Payment Successfully Created",payment));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false ,"Fail to create " , e.toString()));
        }
    }

//Incomplete
    @PostMapping("/assign")
    public  ResponseEntity<ApiResponse>  assignPayment( @RequestBody AssignRequestDTO request){
        try{
             Long paymentId =   request.getPaymentId();
             Long appointmentId = request.getAppointmentId();
            Payment payment = paymentsService.assignPaymentToAppointment(paymentId , appointmentId);
            return  ResponseEntity.ok(new ApiResponse(true ,"Payment Successfully Assigned",payment));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false ,"Fail to Assign " , e.toString()));
        }
    }

    @PutMapping("/update")
    public  ResponseEntity<ApiResponse> updatePayment(@RequestParam Long paymentId,
                                                      @RequestParam Long amount,
                                                      @RequestParam String status ){
        try{
            Payment payment = paymentsService.updatePayment(paymentId , amount , status);
            return  ResponseEntity.ok(new ApiResponse(true ,"Payment Successfully Updated",payment));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false ,"Fail to Update " , e.toString()));
        }
    }


    @DeleteMapping("/delete/{paymentId}")
    public  ResponseEntity<ApiResponse>  assignPayment(@PathVariable Long paymentId ){
        try{
              paymentsService.deletePayment(paymentId );
            return  ResponseEntity.ok(new ApiResponse(true ,"PAYMENT SUCCUSSFULLY  DELETED", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            return  ResponseEntity.ok(new ApiResponse(false ,"FAIL TO  DELETE " , e.toString()));
        }
    }
}
