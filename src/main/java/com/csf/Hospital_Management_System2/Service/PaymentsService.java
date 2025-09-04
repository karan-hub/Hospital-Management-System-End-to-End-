package com.csf.Hospital_Management_System2.Service;

import com.csf.Hospital_Management_System2.Entity.Appointment;
import com.csf.Hospital_Management_System2.Entity.Payment;
import com.csf.Hospital_Management_System2.Entity.type.Status;
import com.csf.Hospital_Management_System2.Repository.AppointmentRepository;
import com.csf.Hospital_Management_System2.Repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentsService {
//    POST /appointments/{appointmentId}/payment — create/initiate payment record
//    GET /appointments/{appointmentId}/payment — get payment details
//    PATCH /appointments/{appointmentId}/payment/status — update payment status (PENDING/PAID/FAILED/REFUNDED)
//    GET /payments — list (page, size, status)
//    GET /payments/{paymentId} — get by id

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment assignPaymentToAppointment(Long paymentId, Long appointmentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setPayment(payment);
        payment.setAppointment(appointment);

        appointmentRepository.save(appointment);
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Transactional
    public Payment updatePayment(Long paymentId, Long amount, Status status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setAmount(amount);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }

    public List<Payment> listPaymentsByStatus(Status status) {
        return paymentRepository.findByStatus(status);
    }

    public List<Payment> listAllPayments() {
        return paymentRepository.findAll();
    }

}
